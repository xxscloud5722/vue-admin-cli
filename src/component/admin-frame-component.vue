<template>
    <div class="admin-frame">
        <div class="side">
            <div class="sidebar-fold">

            </div>
            <a-menu
                    mode="inline"
                    theme="dark"
                    :defaultSelectedKeys="selectedKey"
                    :defaultOpenKeys="selectOpenKey"
                    :inlineCollapsed="collapsed"
                    v-if="menus !=null && menus.length > 0"
                    @select="onSelectMenusItem">
                <template v-for="item of menus">
                    <a-menu-item v-if="item.child == null || item.child.length <= 0"
                                 :class="{'active':item.active}"
                                 :key="item.key">
                        <i :class="item.icon" v-if="item.icon != null && item.icon !== ''"/>
                        &nbsp;
                        {{item.name}}
                    </a-menu-item>
                    <a-sub-menu v-if="item.child != null && item.child.length > 0"
                                :class="{'active':item.active}" :key="item.key">
                        <span slot="title">
                            <i :class="item.icon" v-if="item.icon != null && item.icon !== ''"/>
                            &nbsp;
                            {{item.name}}
                        </span>
                        <a-menu-item v-for="i of item.child" :key="i.key">
                            <i :class="i.icon" v-if="i.icon != null && i.icon !== ''"/>
                            {{i.name}}
                        </a-menu-item>
                    </a-sub-menu>
                </template>
            </a-menu>
        </div>
        <div class="nav">
            <nav>
                <a>新型数据平台-v2.0</a>
                <a class="message">
                    <span>01</span>
                    <i class="iconfont icon-xiaoxi"></i>
                </a>
                <a class="user" v-if="userInfo != null">
                    <span>{{userInfo.name}}</span>
                    &nbsp;
                    <img v-lazy="userInfo.avatarUrl"/>
                </a>
                <a class="close" @click="onClose">登出系统</a>
            </nav>
            <nav>
                <a class="logo">
                    <img src="../static/logo.png"/>
                </a>
            </nav>
        </div>
        <div class="body">
            <slot/>
        </div>

        <!-- 抽屉 消息栏-->
        <a-drawer
                title="系统消息"
                placement="right"
                :closable="false"
                :visible="visible"
                width="400px"
                wrapClassName="admin-message"
        >
            <div class="list">
                <div class="item">
                    <div class="info">
                        <div class="type">产品消息</div>
                        <div class="date">2019-11-11 14:22:23</div>
                        <div class="panel">标记已读</div>

                    </div>
                    <p class="text">腾讯云对象存储COS降价通知</p>
                </div>
                <div class="item">
                    <div class="info">
                        <div class="type">产品消息</div>
                        <div class="date">2019-11-11 14:22:23</div>
                        <div class="panel">标记已读</div>

                    </div>
                    <p class="text">老用户专享福利，续费代金券到账通知！</p>
                </div>
            </div>
        </a-drawer>
    </div>
</template>

<style lang="scss">
    .admin-frame {
        width: 100%;
        height: 100%;
        background: #fff;
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        overflow: hidden;

        & > .nav {
            height: 50px;
            line-height: 50px;
            width: 100%;
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            background: #262626;

            & > nav:first-child {
                position: absolute;
                right: 0;
                display: block;
                display: -webkit-box;

                & > a {
                    cursor: pointer;
                    text-align: center;
                    display: block;
                    font-size: 14px;
                    color: #fff;
                    border-left: 1px solid #000;
                    padding: 0 12px;
                }

                & .message {
                    position: relative;
                    padding: 0 18px;

                    & i {
                        font-size: 19px;
                        font-weight: bold;
                    }

                    & span {
                        position: absolute;
                        right: 7px;
                        top: 10px;
                        background-color: #e54545;
                        color: #fff;
                        width: 20px;
                        height: 16px;
                        font-size: 12px;
                        line-height: 12px;
                        padding: 2px 4px;
                        text-align: center;
                        border-radius: 2px;
                    }
                }

                & .user {
                    & > img {
                        width: 32px;
                        height: 32px;
                        border-radius: 50%;
                    }
                }

                & > a:hover {
                    background: #000000;
                }
            }

            & > nav:last-child {
                position: absolute;
                display: block;
                display: -webkit-box;

                & > a {
                    cursor: pointer;
                    min-width: 80px;
                    text-align: center;
                    display: block;
                    font-size: 14px;
                    color: #fff;

                    & > img {
                        height: 26px;
                    }
                }

                & > a.logo {
                    width: 180px;
                    border-right: 1px solid #000;
                }
            }
        }

        & > .side {
            position: absolute;
            left: 0;
            top: 50px;
            bottom: 0;
            width: 180px;
            background: #333;

            & > .sidebar-fold {
                height: 50px;
                color: #fff;
                line-height: 50px;
                font-size: 13px;
                font-weight: 700;
                text-indent: 20px;
                letter-spacing: 2px;
            }

            & > .ant-menu {
                height: 100%;
                background: #333;

                & .ant-menu-submenu-title {
                    margin: 0 !important;
                    font-size: 13px !important;
                    color: #ddd !important;
                    font-weight: bold !important;
                }


                /*& .active .ant-menu-submenu-title {*/
                /*    background: #00c1de !important;*/
                /*    color: #fff !important;*/

                /*    & .ant-menu-submenu-arrow, & .ant-menu-submenu-arrow:before, & .ant-menu-submenu-arrow:after {*/
                /*        background: #fff !important;*/
                /*    }*/
                /*}*/


                & .ant-menu-item {
                    margin: 0 !important;
                    font-size: 13px !important;
                    color: #ddd !important;
                    font-weight: bold !important;
                }

                & .ant-menu-sub {
                    background: rgb(38, 38, 38) !important;
                    box-shadow: none !important;

                    & li.ant-menu-item {
                        padding-left: 32px !important;
                        font-weight: 400 !important;
                    }

                    & li.ant-menu-item:hover {
                        background: #444 !important;
                    }
                }

                & .ant-menu-item-selected {
                    color: #3d91ff !important;
                    background: transparent !important;
                }

                & .ant-menu-submenu-open {
                    background: rgb(38, 38, 38) !important;
                }

                & .ant-menu-submenu-title:hover, & .ant-menu-item:hover {
                    background: #444 !important;
                }
            }
        }

        & > .body {
            background: #f2f2f2 !important;
            position: absolute;
            left: 180px;
            top: 50px;
            right: 0;
            bottom: 0;
            overflow-y: auto;
        }

    }

    .admin-message {
        & .ant-drawer-wrapper-body {
            & .ant-drawer-header {
                padding: 13px 24px !important;
            }
        }

        & .ant-drawer-body {
            padding: 0;
        }

        & .list {
            & > .item {
                border-bottom: 1px solid #ddd;
                padding: 8px 12px;
                cursor: pointer;

                & > .info {
                    height: 24px;
                    line-height: 24px;
                    display: flex;
                    margin: 0 0 4px 0;

                    & > .type {
                        flex: 1;
                        width: 100%;
                        text-align: left;
                        color: #888888;
                    }

                    & > .date {
                        flex: 1;
                        width: 100%;
                        text-align: right;
                        color: #888888;
                    }

                    & > .panel {
                        flex: 1;
                        width: 100%;
                        text-align: right;
                        display: none;
                        color: #006eff;
                    }
                }

                & > .text {
                    font-size: 13px;
                    margin: 0;
                    height: 24px;
                    line-height: 24px;
                    color: #000;
                }
            }

            & > .item:hover {
                background: #eee;

                & .info > .panel {
                    display: block;
                }

                & .info > .date {
                    display: none;
                }
            }
        }
    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import Utils from '../utils/utils';

    declare const window: any;

    @Component
    export default class AdminFrameComponent extends Vue {
        public collapsed: boolean = false;
        public selectedKey: string[] | null = [];
        public selectOpenKey: string[] | null = [];
        public openKey: any = null;
        public menus: any[] = [];
        public visible: boolean = false;
        public userInfo: any = null;

        public created() {

        }

        public async mounted() {
            const result = (await this.$api.getMenusList.execute()).asObject();
            if (result.success) {
                const menus: any[] = [];
                result.data.forEach(it => {
                    if (it.pid === '0') {
                        it.key = it.id;
                        menus.push(it);
                    }
                });
                menus.forEach(it => {
                    for (const item of result.data) {
                        if (item.pid === it.id) {
                            it.child = it.child || [];
                            item.key = item.id;
                            it.child.push(item);
                        }
                    }
                });


                menus.forEach(it => {
                    if (it.link === window.location.pathname) {
                        this.selectedKey = [it.id];
                        this.selectOpenKey = [it.id];
                        return;
                    }
                    if (it.child == null) {
                        return;
                    }
                    let flag = false;
                    for (const c of it.child) {
                        if (c.link === window.location.pathname) {
                            this.selectedKey = [c.id];
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        this.selectOpenKey = [it.id];
                    }
                });


                this.menus = menus;
                Utils.setItem('menus', this.menus);
            }
            this.userInfo = window.userInfo;
        }

        public async onSelectMenusItem(event: any) {
            let item: any = null;
            for (const it of this.menus) {
                if (it.key === event.key) {
                    item = it;
                    continue;
                }
                if (it.child != null) {
                    for (const t of it.child) {
                        if (t.key === event.key) {
                            item = t;
                        }
                    }
                }
            }

            if (item != null) {
                this.$router.push(item.link);
            }
        }

        public async onClose() {
            this.$confirm({
                title: '您确定要登出系统吗?',
                content: '登出系统后您将需要重启登陆',
                onOk: async () => {
                    window.userInfo = null;
                    Utils.clear();
                    window.location.href = window.location.origin;
                },
                onCancel: async () => {

                },
            });
        }
    }
</script>