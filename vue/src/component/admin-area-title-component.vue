<template>
    <div class="admin-area-title">
        <div class="header">
            <div class="title">
                {{label}}
            </div>
            <div class="console">
                <slot></slot>
            </div>
        </div>
        <div class="tabs" v-if="aTabs != null && aTabs.length > 0">
            <div class="item" v-for="item of aTabs" :class="item.active ? 'active':''" @click="onSelectItem(item)">
                {{item.label}}
            </div>
        </div>
    </div>
</template>

<style lang="scss">
    .admin-area-title {
        border-bottom: 1px solid rgb(221, 221, 221);

        & > .header {
            width: 100%;
            height: 50px;
            background: #fff;
            display: flex;
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
        }

        & > .tabs {
            background: #fff;
            height: 30px;
            padding: 0 20px;
            display: flex;

            & > .item {
                position: relative;
                cursor: pointer !important;
                line-height: 28px;
                height: 30px;
                min-width: 96px;
                text-align: center;
                font-size: 14px;
                color: #333;
                font-weight: bold;
                margin-right: 24px;
            }

            & > .item.active:before {
                content: ' ';
                display: block;
                width: 100%;
                height: 2px;
                border-bottom: 2px solid #006eff;
                position: absolute;
                left: 0;
                right: 0;
                bottom: 0;
            }
        }
    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import {Prop} from 'vue-property-decorator';


    @Component
    export default class AdminAreaTitleComponent extends Vue {
        @Prop({
            type: String,
            default: ''
        })
        public label: string;

        @Prop({
            type: Array,
            default: () => []
        })
        public tabs: any[];

        public aTabs: any[] = [];

        public async mounted() {
            this.tabs.forEach(it => {
                this.aTabs.push({label: it.label, value: it.value, active: false});
            });
            if (this.aTabs.length > 0) {
                this.aTabs[0].active = true;
            }
        }

        public onSelectItem(item) {
            let s: any = null;
            this.aTabs.forEach(it => {
                if (it.active) {
                    s = it;
                }
                it.active = false;
            });
            item.active = true;
            if (s != null && item.value !== s.value) {
                this.$emit('change', item);
            }
        }
    }
</script>