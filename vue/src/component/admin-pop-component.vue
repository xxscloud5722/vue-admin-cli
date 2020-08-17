<template>
    <div class="admin-pop" v-if="value">
        <div class="header">
            <div class="back">
                <div class="icon" @click="onBack()"></div>
            </div>
            <div class="title">{{label}}</div>
        </div>
        <div class="view">
            <slot></slot>
        </div>
    </div>
</template>

<style lang="scss">
    .admin-pop {
        width: 100%;
        position: absolute;
        z-index: 996;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        background: #f2f2f2 !important;
        overflow-y: auto;

        & > .header {
            width: 100%;
            height: 50px;
            background: #fff;
            display: flex;
            border-bottom: 1px solid rgb(221, 221, 221);
            padding: 0 20px;

            & > .title {
                line-height: 50px;
                color: #333;
                font-size: 16px;
                font-weight: 700;
                width: 200px;
            }

            & > .console {
                height: 30px;
                flex: 1;
                margin: 10px 0;
            }

            & > .back {
                width: 30px;
                height: 50px;
                margin-right: 10px;

                & .icon {
                    position: relative;
                    width: 24px;
                    height: 24px;
                    display: block;
                    margin: 13px auto;
                    cursor: pointer;
                }

                & .icon:hover {
                    background: #eee;
                }

                & .icon:before {
                    content: ' ';
                    display: block;
                    background: url("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxNiIgaGVpZ2h0PSIxNiI+PHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGZpbGw9IiMwMDZlZmYiIGQ9Ik0xNSA3SDQuN2w0LjQtNC42TDcuNyAxIDEgOGw2LjcgNyAxLjQtMS40TDQuNyA5SDE1eiIvPjwvc3ZnPg==");
                    background-size: 100% 100%;
                    width: 18px;
                    height: 18px;
                    position: absolute;
                    left: 3px;
                    top: 3px;
                }
            }
        }

        & > .view {
            & > .content {
                box-shadow: 0 2px 3px 0 rgba(0, 0, 0, .2);
                background: #fff;
                margin: 20px 40px;
                padding: 20px;
            }
        }

    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import {Model, Prop} from 'vue-property-decorator';

    declare const window: any;

    @Component
    export default class AdminAreaTitleComponent extends Vue {
        @Prop({
            type: String,
            default: ''
        })
        public label: string;


        @Prop({
            type: Boolean,
            default: false
        })
        @Model('change')
        public value: boolean;

        public change() {
            this.$emit('change', this.value);
        }

        public onBack() {
            this.$emit('change', false);
         }
    }
</script>