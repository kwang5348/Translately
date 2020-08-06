import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Contents from '../views/Contents.vue'
// accounts
import LoginView from '../views/accounts/LoginView.vue'
import SignupView from '../views/accounts/SignupView.vue'
import CompleteView from '../views/accounts/CompleteView.vue'

// contents
import ArticleCreateView from '../views/contents/ArticleCreateView.vue'
import Community from '../views/contents/Community.vue'
import CreateCaption from '../views/contents/CreateCaption.vue'
import MyPage from '../views/contents/MyPage.vue'
import MyProject from '../views/contents/MyProject.vue'
import TutorialView from '../views/contents/TutorialView.vue'
import UserView from '../views/contents/UserView.vue'
Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/contents',
    name: 'Contents',
    component: Contents,
    children: [{
      path: 'tutorial',
      name: 'TutorialView',
      component: TutorialView
    },
    {
      path: 'community',
      name: 'Community',
      component: Community
    },
    {
      path: 'myproject',
      name: 'MyProject',
      component: MyProject
    },
    {
      path: 'mypage',
      name: 'MyPage',
      component: MyPage
    },
  ]},
  {
    path: '/accounts/login',
    name: 'LoginView',
    component: LoginView
  },
  {
    path: '/accounts/signup',
    name: 'SignupView',
    component: SignupView
  },
  {
    path: '/accounts/complete',
    name: 'CompleteView',
    component: CompleteView
  },
  {
    path: '/contents/create',
    name: 'ArticleCreateView',
    component: ArticleCreateView,
    beforeEnter(from, to, next) {
      if (Vue.$cookies.isKey('auth-token')) {
        next()
      } else {
        next('/accounts/login')
      }
    }
  },
  
  {
    path: '/user',
    name: 'UserView',
    component: UserView
  },
  {
    path: '/createcaption',
    name: 'CreateCaption',
    component: CreateCaption
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
