//@ts-nocheck
declare const window: any;
declare const document: any;
export default class Utils {

    static setSessionItem(key: string, obj: any) {
        if (obj == null) {
            window.sessionStorage.removeItem(key);
            return;
        }
        if (typeof (obj) === 'string' || typeof (obj) === 'number') {
            return window.sessionStorage.setItem(key, obj);
        }
        window.sessionStorage.setItem(key, JSON.stringify(obj));
    }

    static setItem(key: string, obj: any) {
        if (obj == null) {
            window.localStorage.removeItem(key);
            return;
        }
        if (typeof (obj) === 'string' || typeof (obj) === 'number') {
            return window.localStorage.setItem(key, obj);
        }
        window.localStorage.setItem(key, JSON.stringify(obj));
    }

    static clear() {
        window.localStorage.clear();
    }

    static getSessionItem(key: string) {
        const json = window.sessionStorage.getItem(key);
        if (json != null && (json[0] === '{' || json[0] === '[')) {
            return JSON.parse(json);
        }
        return json;
    }

    static getItem(key: string) {
        const json = window.localStorage.getItem(key);
        if (json != null && (json[0] === '{' || json[0] === '[')) {
            return JSON.parse(json);
        }
        return json;
    }

    static getParam(key: string) {
        const value = window.location.href.split('?')[1];
        if (value == null) {
            return null;
        }
        let m = '';
        decodeURIComponent(value).split('&').forEach(it => {
            const ss = it.trim().split('=');
            if (ss[0].toString().toLocaleLowerCase() === key.toLocaleLowerCase()) {
                m = ss[1];
            }
        });
        return m;
    }

    static dateFormat(fmt, date) {
        let ret;
        const opt = {
            "yyyy": date.getFullYear().toString(),        // 年
            "MM": (date.getMonth() + 1).toString(),     // 月
            "dd": date.getDate().toString(),            // 日
            "HH": date.getHours().toString(),           // 时
            "mm": date.getMinutes().toString(),         // 分
            "ss": date.getSeconds().toString()          // 秒
        };
        for (let k in opt) {
            ret = new RegExp("(" + k + ")").exec(fmt);
            if (ret) {
                fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")));
            }
        }
        return fmt;
    }

    public static copy(value: string) {
        const element = document.createElement('input');
        document.body.append(element);
        element.value = value;
        element.select();
        document.execCommand("Copy");
        element.remove();
    }
}