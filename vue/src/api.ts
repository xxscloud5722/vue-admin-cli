//@ts-nocheck
import Axios, {AxiosRequestConfig} from 'axios';
import Utils from './utils/utils';
import Vue from 'vue';

declare const window: any;
declare const FormData: any;


export class HttpResponse {
    private data;

    constructor(data) {
        this.data = data;
    }

    public asObject() {
        return this.data.data;
    }
}


// API地址配置
export interface ApiType {
    url: string;
    method: string;
    format: string;

    execute(args?: any): Promise<HttpResponse>;
}


export class Api {
    public login: ApiType = {url: '/system/login', method: 'post', format: 'json'};
    public upload: ApiType = {url: '/index/upload', method: 'post', format: 'file'};
    public getMenusList: ApiType = {url: '/system/getMenusList', method: 'post', format: 'json'};
    public updatePassword: ApiType = {url: '/user/updatePassword', method: 'post', format: 'json'};



    public getUserList: ApiType = {url: '/system/getUserList', method: 'post', format: 'json'};
    public getRoleList: ApiType = {url: '/system/getRoleList', method: 'post', format: 'json'};
    public getGroupList: ApiType = {url: '/system/getGroupList', method: 'post', format: 'json'};
    public saveGroup: ApiType = {url: '/system/saveGroup', method: 'post', format: 'json'};
    public saveRole: ApiType = {url: '/system/saveRole', method: 'post', format: 'json'};
    public removeRole: ApiType = {url: '/system/removeRole', method: 'post', format: 'json'};
    public saveUser: ApiType = {url: '/system/saveUser', method: 'post', format: 'json'};
    public setUserStatus: ApiType = {url: '/system/setUserStatus', method: 'post', format: 'json'};
    public removeUser: ApiType = {url: '/system/removeUser', method: 'post', format: 'json'};
    public removeGroup: ApiType = {url: '/system/removeGroup', method: 'post', format: 'json'};
    public getGroupUserList: ApiType = {url: '/system/getGroupUserList', method: 'post', format: 'json'};
    public getPermissionList: ApiType = {url: '/system/getPermissionList', method: 'post', format: 'json'};
    public getRolePermissionList: ApiType = {
        url: '/system/getRolePermissionList',
        method: 'post',
        format: 'json'
    };
    public transform: ApiType = {url: '/index/transform', method: 'post', format: 'json'};

    public axios_instance = Axios.create();


    constructor() {
        this.axios_instance.interceptors.request.use(async (config: AxiosRequestConfig) => {
            const token = JSON.parse(window.localStorage.getItem('userInfo') || '{}').token;
            if (token != null && token !== '') {
                config.headers.token = token;
            }
            return config;
        });

        this.axios_instance.interceptors.response.use();

        const $this = this;
        for (let name in $this) {
            if ($this.hasOwnProperty(name)) {
                const item: any = this[name];
                if (item != null && item.url != null) {
                    item.execute = (args?: any) => {
                        return this.executeRun(item, args);
                    }
                }
            }
        }
    }

    public executeRun(c: any, body?: any): Promise<HttpResponse> {
        // 缓存
        if (c.expireTime != null && !isNaN(Number(c.expireTime))) {
            const r = Utils.getItem(c.url + '_' + JSON.stringify(body));
            if (r != null && r !== '' && r.expireTime >= new Date().getTime()) {
                return new Promise(resolve => {
                    resolve(new HttpResponse(r));
                });
            }
        }
        const config = {
            headers: {},
        };
        switch (c.method) {
            case 'post':
                switch (c.format) {
                    case 'json':
                        config.headers = {'Content-Type': 'application/json; charset=UTF-8'};
                        break;
                    case 'form':
                        config.headers = {'Content-Type': 'application/x-www-form-urlencoded'};
                        if (body != null) {
                            const form = new FormData();
                            for (const name in body) {
                                if (body.hasOwnProperty(name)) {
                                    form.append(name, body[name]);
                                }
                            }
                            body = form;
                        }
                        break;
                    case 'file':
                        config.headers = {'Content-Type': 'multipart/form-data'};
                        if (body != null) {
                            const form = new FormData();
                            for (const name in body) {
                                if (body.hasOwnProperty(name)) {
                                    form.append(name, body[name]);
                                }
                            }
                            body = form;
                        }
                        break;
                }
                return this.axios_instance
                    .post(window.C.BASE_URL + c.url, body, config)
                    .then((r: any) => {
                        // 是否需要验证
                        if (r != null && r.data.success === false && r.data.code === '-1' && (c.check == null || c.check)) {
                            window.userInfo = null;
                            Utils.clear();
                            window.location.href = window.location.origin;
                        }
                        // 是否需要缓存
                        if (c.expireTime != null && !isNaN(Number(c.expireTime))) {
                            r.expireTime = new Date().getTime() + Number(c.expireTime);
                            Utils.setItem(c.url + '_' + JSON.stringify(body), r);
                        }
                        return new HttpResponse(r);
                    })
                    .catch(r => {
                        // 如果401
                        if (r.toString().indexOf('Request failed with status code 401') > -1) {
                            window.userInfo = null;
                            Utils.clear();
                            window.location.href = window.location.origin;
                        }
                        return new HttpResponse({
                            data: {
                                data: {
                                    success: false,
                                    message: r.message,
                                },
                            },
                        });
                    });
            case 'get':
                return this.axios_instance
                    .get(window.C.BASE_URL + c.url, body)
                    .then((r: any) => {
                        // 是否需要缓存
                        if (c.expireTime != null && !isNaN(Number(c.expireTime))) {
                            r.expireTime = new Date().getTime() + Number(c.expireTime);
                            Utils.setItem(c.url + '_' + JSON.stringify(body), r);
                        }
                        return new HttpResponse(r);
                    })
                    .catch(r => {
                        return new HttpResponse({
                            data: {
                                data: {
                                    success: false,
                                    message: r.message,
                                },
                            },
                        });
                    });
            default:
                break;
        }
    };
}

declare module 'vue/types/vue' {
    interface Vue {
        $api: Api;
    }
}
Vue.prototype.$api = new Api();