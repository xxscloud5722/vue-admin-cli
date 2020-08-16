import App from './app.vue';
import Vue from 'vue';
import router from './router/router';
import VueRouter from 'vue-router';
import Antd from 'ant-design-vue';
import VueLazyload from 'vue-lazyload';
import 'ant-design-vue/dist/antd.less';
import './api';
import Utils from './utils/utils';


declare const window: any;
Vue.use(VueRouter);
Vue.use(Antd);
Vue.use(VueLazyload);


// 加载身份
window.userInfo = Utils.getItem('userInfo');

// tslint:disable-next-line:no-unused-expression
new Vue({
    el: '#app',
    router,
    render: h => h(App),
});
