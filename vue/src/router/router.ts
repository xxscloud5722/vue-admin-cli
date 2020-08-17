import VueRouter from 'vue-router';

declare const window: any;
const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/login',
            component: (() => import('../login.vue')),
        },
        {
            path: '/system/*',
            component: (() => import('../system.module/system.vue')),
            children: [
                {
                    path: '/system/home',
                    component: (() => import('../system.module/home-component.vue')),
                },










                {
                    path: '/system/user',
                    component: (() => import('../system.module/user-component.vue')),
                },
                {
                    path: '/system/user-group',
                    component: (() => import('../system.module/user-group-component.vue')),
                },
                {
                    path: '/system/user-role',
                    component: (() => import('../system.module/user-role-component.vue')),
                }
            ]
        },
    ]
});
router.beforeEach((to, _, next) => {
    if (to.path === '/login') {
        next();
    } else {
        if (window.userInfo != null) {
            next();
        } else {
            next('/login');
        }
    }
});
export default router;