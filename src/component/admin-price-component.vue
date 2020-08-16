<template>
    <div class="admin-price">
        <div class="text">{{label}}</div>
        <input type="text" v-model="value1" @change="change"/>
        <div class="split">至</div>
        <input type="text" v-model="value2" @change="change"/>
    </div>
</template>

<style lang="scss">
    .admin-price {
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

        & .split {
            border-left: 1px solid #ddd;
            border-right: 1px solid #ddd;
            padding: 0 8px;
            font-size: 12px;
            background: #efefef;
            text-align: center;
        }

        & input {
            border: none;
            margin: 0;
            font-size: 12px;
            outline: none;
            padding: 0 6px;
            flex: 1;
            width: 100%;
        }


    }
</style>
<script lang="ts">
    import Vue from 'vue';
    import Component from 'vue-class-component';
    import {Prop} from 'vue-property-decorator';

    @Component
    export default class AdminPriceComponent extends Vue {
        @Prop({
            type: String,
            default: ''
        })
        public label: string;


        public value1: string = '';
        public value2: string = '';

        public change() {
            this.$emit('change', [this.value1, this.value2]);
        }

    }


</script>