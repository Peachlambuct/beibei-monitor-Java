<script setup>
import {onBeforeUnmount, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import {AttachAddon} from "xterm-addon-attach/src/AttachAddon";
import {Terminal} from "xterm";
import "xterm/css/xterm.css";
import axios from "axios";

const props = defineProps({
  id: Number
})
const emits = defineEmits(['dispose'])

const terminalRef = ref()
const url = axios.defaults.baseURL.replace('http', 'ws');
const socket = new WebSocket(`${url}/terminal/${props.id}`)
socket.onclose = evt => {
  if(evt.code !== 1000) {
    ElMessage.warning(`连接失败: ${evt.reason}`)
  } else {
    ElMessage.success('远程SSH连接已断开')
  }
  emits('dispose')
}
const closeConnection = () => {
  socket.close();
  term.dispose();
  emits('dispose');
};
const attachAddon = new AttachAddon(socket);
const term = new Terminal({
  lineHeight: 1.5,
  rows: 20,
  fontSize: 14,
  fontFamily: "Monaco, Menlo, Consolas, 'Courier New', monospace",
  fontWeight: "bold",
  theme: {
    background: '#000000'
  },
  // 光标闪烁
  cursorBlink: true,
  cursorStyle: 'underline',
  scrollback: 100,
  tabStopWidth: 4,
});
term.loadAddon(attachAddon);
term.resize(100,50)
onMounted(() => {
  term.open(terminalRef.value)
  term.focus()
})

onBeforeUnmount(() => {
  socket.close()
  term.dispose()
  emits('dispose')
})

defineExpose({
  closeConnection,
});

</script>

<template>
  <div ref="terminalRef" class="xterm"/>
</template>

<style scoped>

</style>
