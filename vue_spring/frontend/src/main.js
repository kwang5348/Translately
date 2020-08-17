import Vue from 'vue'
import App from './App.vue'
import GoogleSignInButton from 'vue-google-signin-button-directive'
import router from './router'
import VueCookies from 'vue-cookies'
// 부트스트랩 뷰
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// 뷰티파이
import vuetify from './plugins/vuetify';

Vue.use(VueCookies)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  GoogleSignInButton,
  vuetify,
  render: h => h(App)
}).$mount('#app')