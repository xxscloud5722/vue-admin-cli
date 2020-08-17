<template>
    <div class="app">
        <a-config-provider :locale="locale">
            <router-view/>
        </a-config-provider>
    </div>
</template>

<style lang="scss">
    @import "static/font/iconfont.css";

    body,
    html {
        padding: 0;
        margin: 0;
    }

    body,
    html,
    h1,
    h2,
    h3,
    h4,
    h5,
    h6,
    div,
    p,
    em,
    b {
        font-family: PingFangSC, helvetica neue, hiragino sans gb, arial,
        microsoft yahei ui, microsoft yahei, simsun, sans-serif;
        font-size: 12px;
    }

    .ant-modal .ant-modal-title {
        font-size: 14px !important;
    }

    .node .person {
        width: 15em !important;
        margin-left: -1.2em !important;
    }

    .node .person .name {
        white-space: pre-line !important;
        height: 5em !important;
        line-height: 1.3 !important;
    }

    .extend_handle {
        padding: 0 !important;
        margin-left: 10px !important;
    }

    .ant-form label,
    .ant-input {
        font-size: 13px !important;
    }

    .x-row {
        display: flex !important;

        & .ant-form-item:first-child {
            margin-right: 20px;
        }

        & .ant-form-item {
            flex: 1;
            width: 100%;
        }
    }

    .ant-divider {
        background: #aaa !important;
    }

    .ant-btn {
        padding: 0 16px !important;
        height: 32px !important;

        & > span {
            font-size: 12px !important;
        }
    }

    .ant-btn.ant-btn-primary {
        background: rgb(0, 110, 255) !important;
        border: 1px solid #006eff !important;
    }

    .ant-radio-button-wrapper.ant-radio-button-wrapper-checked {
        background: #006eff !important;
        border-color: #006eff !important;
        color: #fff !important;
        box-shadow: none !important;
    }

    .ant-tabs .ant-tabs-tab {
        font-size: 13px !important;
    }

    hr {
        padding: 0;
        border: none;
        border-bottom: 1px solid #ddd;
        margin: 15px 0;
    }

    .ant-badge-status-text {
        font-size: 12px !important;
    }

    .ant-transfer-list {

    }

    .ant-transfer-operation {
        & .ant-btn {
            padding: 0 6px !important;
        }

        & .ant-btn-primary[disabled] {
            background: #f5f5f5 !important;
            border-color: rgba(0, 0, 0, 0.1) !important;
        }
    }

    .color-blue {
        color: #1890ff !important;
    }

    .color-pink {
        color: #eb2f96;
    }

    .color-red {
        color: #f5222d !important;
    }

    .color-orange {
        color: #fa8c16 !important;
    }

    .color-green {
        color: #52c41a;
    }

    .color-cyan {
        color: #13c2c2 !important;
    }


    .color-purple {
        color: #722ed1 !important;
    }

    .edit-preview {
        max-width: 560px;

        & img {
            width: 100%;
            display: block;
        }

        & p {
            margin: 0 20px;
            display: block;
        }
    }
</style>

<script lang="ts">
    import Vue from "vue";
    import Component from "vue-class-component";
    import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN';

    @Component
    export default class App extends Vue {
        public locale = zhCN;
    }
</script>