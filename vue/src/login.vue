<template>
    <div class="login">
        <div class="header">
            <div class="logo">
                <img src="https://id.163yun.com/res/images/yunxinLogo@2x.png">
            </div>
        </div>
        <div class="main">
            <div class="cover">
                <img src="https://nos.netease.com/cloud-website-bucket/20200312163718ba122949-219d-4b33-bfb0-3668506508f6.png">
            </div>
            <div class="panel">
                <p class="title">悦米·系统·登录</p>
                <div class="input">
                    <input type="text" placeholder="账号/手机号" v-model="userInfo.account"/>
                </div>
                <div class="input">
                    <input type="password" placeholder="登陆密码" v-model="userInfo.password"/>
                </div>
                <div class="submit" @click="onLogin" v-if="loading">登陆</div>
                <div class="submit loading" v-else>Loading ...</div>
            </div>
        </div>
        <div class="main-text">
            <p>悦米信息&nbsp;©2020&nbsp;&nbsp;&nbsp;&nbsp;浙ICP备00000000号-0</p>
        </div>
    </div>
</template>

<style lang="scss">
    .login {
        width: 100%;
        height: 100vh;
        background: #f7f7f7;

        & > .header {
            height: 60px;
            background: #ffffff;
            padding: 0 80px;

            & > .logo {
                display: block;
                width: 120px;
                height: 100%;

                & > img {
                    width: 120px;
                    height: auto;
                    display: block;
                    position: absolute;
                    margin: 11px 0 0 0;
                }
            }
        }

        & > .main {
            margin: 80px auto 0 auto;
            background: #ffffff;
            width: 900px;
            box-shadow: 0 2px 3px 0 rgba(213, 213, 213, 0.8);
            display: flex;

            & > .cover {
                width: 100%;
                height: 520px;
                flex: 1;
                overflow: hidden;

                & > img {
                    height: 100%;
                    object-fit: cover;
                    display: block;
                    margin: auto;
                }
            }

            & > .panel {
                padding: 100px 0;
                min-width: 400px;
                height: auto;

                & > .title {
                    margin: 0;
                    padding: 10px 0;
                    text-align: center;
                    color: #222;
                    font-size: 22px;
                    letter-spacing: 1px;
                }

                & > .input {
                    border: 1px solid #d9d9d9;
                    margin: 20px 40px;

                    & input {
                        border: none;
                        width: 100%;
                        padding: 15px 20px;
                        outline: none;
                        font-size: 13px;
                        line-height: 42px;
                        height: 42px;
                    }
                }

                & > .submit {
                    display: block;
                    margin: 20px 40px 0 40px;
                    text-align: center;
                    color: #fff;
                    background: #387ee8;
                    height: 45px;
                    line-height: 45px;
                    cursor: pointer;
                    font-size: 15px;
                    font-weight: 500;
                }

                & > .submit.loading {
                    background: #508de8 !important;
                }

                & > .submit:hover {
                    background: #508de8;
                }
            }
        }

        & > .main-text {
            text-align: center;
            margin-top: 20px;

            & > p {
                font-size: 14px;
                color: #999999;
                margin: 0;
            }
        }
    }
</style>

<script lang="ts">
    import Vue from "vue";
    import Component from "vue-class-component";
    import Utils from "./utils/utils";

    declare const window;

    @Component
    export default class App extends Vue {
        public loading: boolean = true;
        public userInfo: any = {
            account: '',
            password: ''
        };

        public async onLogin() {
            this.loading = false;
            const result = (await this.$api.login.execute(this.userInfo)).asObject();
            this.loading = true;
            if (result.success) {
                window.userInfo = result.data;
                Utils.setItem('userInfo', result.data)
                await this.$router.push('/system/home');
            } else {
                this.$message.error(result.message);
            }
        }
    }
</script>