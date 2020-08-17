<template>
    <div class="system">
        <admin-frame-component>
            <router-view/>
        </admin-frame-component>
    </div>
</template>

<style lang="scss">
    .system {

    }
</style>

<script lang="ts">
    import Vue from "vue";
    import Component from "vue-class-component";
    import AdminFrameComponent from "../component/admin-frame-component.vue";

    @Component({
        components: {AdminFrameComponent}
    })
    export default class SystemModule extends Vue {

    }
</script>