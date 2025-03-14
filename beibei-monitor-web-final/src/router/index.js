import { createRouter, createWebHistory } from 'vue-router'
import { unauthorized } from "@/net";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'welcome',
            component: () => import('@/views/WelcomeView.vue'),
            children: [
                {
                    path: '',
                    name: 'welcome-login',
                    component: () => import('@/views/welcome/LoginPage.vue')
                }, {
                    path: 'forget',
                    name: 'welcome-forget',
                    component: () => import('@/views/welcome/ForgetPage.vue')
                }
            ]
        }, {
            path: '/index',
            name: 'index',
            component: () => import('@/views/IndexView.vue'),
            children: [
                {
                    path: '',
                    name: 'overview',
                    component: () => import('@/views/main/Overview.vue')
                }, {
                    path: 'security',
                    name: 'security',
                    component: () => import('@/views/main/Security.vue')
                }, {
                    path: 'child',
                    name: 'child',
                    component: () => import('@/views/main/ChildManage.vue')
                }, {
                    path: 'manage',
                    name: 'manage',
                    component: () => import('@/views/main/Manage.vue')
                }, {
                    path: 'sshManage',
                    name: 'ssh-manage',
                    component: () => import('@/views/main/SSHManage.vue')
                }, {
                    path: 'earlyWarning',
                    name: 'earlyWarning',
                    component: () => import('@/views/main/EarlyWarningManage.vue')
                },{
                    path: 'taskAssignment',
                    name: 'taskAssignment',
                    component: () => import('@/views/main/TaskAssignment.vue')
                },{
                    path: 'devArrangement',
                    name: 'devArrangement',
                    component: () => import('@/views/main/DevArrangement.vue')
                },{
                    path: 'devArrangement',
                    name: 'devArrangement',
                    component: () => import('@/views/main/DevArrangement.vue')
                },{
                    path: 'devTask',
                    name: 'devTask',
                    component: () => import('@/views/main/DevTask.vue')
                }
            ]
        }
    ]
})

router.beforeEach((to, from, next) => {
    const isUnauthorized = unauthorized()
    if(to.name.startsWith('welcome') && !isUnauthorized) {
        next('/index')
    } else if(to.fullPath.startsWith('/index') && isUnauthorized) {
        next('/')
    } else {
        next()
    }
})

export default router
