import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";
import { Quasar } from 'quasar'

import '@/assets/css/element.less'
import 'flag-icon-css/css/flag-icons.min.css'
import {createPinia} from "pinia";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import '@quasar/extras/material-icons/material-icons.css'
import 'quasar/src/css/index.sass'

axios.defaults.baseURL = 'http://154.3.1.23:8080'
// axios.defaults.baseURL = 'http://localhost:8080'

const app = createApp(App)

const pinia = createPinia()
app.use(pinia)
pinia.use(piniaPluginPersistedstate)
app.use(router)
app.use(Quasar, {
    plugins: {}, // import Quasar plugins and add here
})
app.mount('#app')
