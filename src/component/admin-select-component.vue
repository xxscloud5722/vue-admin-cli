<template>
    <div class="admin-select">
        <div class="text">{{label}}</div>
        <a-select class="select" @change="handleChange" v-if="data != null && data.length > 0"
                  :defaultValue="defaultValue">
            <a-select-option :value="item.value" v-for="item of data" :key="item.value">{{item.name}}</a-select-option>
        </a-select>
    </div>
</template>

<style lang="scss">
    .admin-select {
        display: flex;
        line-height: 30px;
        height: 32px;
        background: #fff;
        border: 1px solid #ddd;
        width: 200px;
        box-sizing: border-box;

        & .text {
            padding: 0 8px;
            font-size: 12px;
            background: #efefef;
            text-align: center;
            border-right: 1px solid #ddd;
        }

        & .select {
            display: block !important;
            border: none !important;
            margin: 0 !important;
            font-size: 12px !important;
            outline: none !important;
            flex: 1;
            width: 100% !important;
            height: 30px !important;
            padding: 0 !important;
            box-shadow: none !important;
        }

        & .ant-select-selection {
            border: none !important;
            height: 30px !important;
            box-shadow: none !important;
        }

        & .ant-select-selection-selected-value {
            font-size: 12px;
        }

    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import {Model, Prop} from 'vue-property-decorator';

    @Component
    export default class AdminSelectComponent extends Vue {
        @Prop({
            type: String,
            default: ''
        })
        public label: string;

        @Prop({
            type: Array,
            default: []
        })
        public data: any[];

        @Prop({
            type: String,
            default: ''
        })
        @Model('change')
        public select: string;

        @Prop({
            type: String,
            default: ''
        })
        public defaultValue: string;

        public value: string;

        public async mounted() {
            if (this.data.length > 0) {
                this.defaultValue = this.data[0].value;
            }
        }

        public async handleChange(event: any) {
            this.$emit('change', event);
        }
    }


</script>