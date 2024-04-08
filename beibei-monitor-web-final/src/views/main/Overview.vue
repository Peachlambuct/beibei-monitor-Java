<script setup>
import OverviewCard from "@/component/OverviewCard.vue";


</script>

<template>
  <div style="margin: 0 50px">
    <div style="font-size: 25px;font-weight: bold">服务器总览</div>
    <div style="font-size: 15px;color: grey">查看本系统管理的全部服务器的基本信息</div>

    <el-divider style="margin: 10px 0"/>

    <div style="width: 100%;height: 400px;display: flex">
      <div style="flex: 5;">
        <div style="height: 50%;background-color: #45c245;border-radius: 20px">
          <span style="font-weight: bold;font-size: 30px;margin: 5px 10px">此服务器运行指标:</span>


        </div>
        <div style="height: 50%;background-color: #c7cd6f;border-radius: 20px">
          <span style="font-weight: bold;font-size: 30px;margin: 5px 10px">昨日服务器告警:</span>
          <span style="font-weight: bold;font-size: 27px;margin: 0 10px">1</span>
          <div style="margin: 0 10px">
            <div>XXXX服务器：XX时XX分 CPU内存超标... (已提醒)</div>
            <div>XXXX服务器：XX时XX分 CPU内存超标... (已提醒)</div>
            <div>XXXX服务器：XX时XX分 CPU内存超标... (已提醒)</div>
            <div>XXXX服务器：XX时XX分 CPU内存超标... (已提醒)</div>
            <div>XXXX服务器：XX时XX分 CPU内存超标... (已提醒)</div>
          </div>
        </div>
      </div>
      <div style="flex: 8;background-color: #3991ac;">
        <span style="font-weight: bold;font-size: 30px;margin: 5px 10px">在线服务器列表</span>
        <span style="font-weight: bold;font-size: 27px;margin: 0 10px">13 / 50</span>
        <div style="display: flex;flex-wrap: wrap;gap: 10px">
          <div style="margin: 0 10px">
            <div>XXXX服务器：(中国)</div>
            <div>XXXX服务器：(中国)</div>
            <div>XXXX服务器：(中国)</div>
            <div>XXXX服务器：(香港)</div>
            <div>XXXX服务器：(美国)</div>
          </div>
        </div>
      </div>
    </div>
    <el-divider style="margin: 25px 0"/>
    <div style="background-color: #606060;height: 500px;">
      <div style="font-weight: bold;font-size: 30px;margin: 5px 10px" >开发进度</div>

    </div>
  </div>
</template>

<style scoped>

</style>