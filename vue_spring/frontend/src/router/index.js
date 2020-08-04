import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import LoginView from '../views/accounts/LoginView.vue'
import SignupView from '../views/accounts/SignupView.vue'
import CompleteView from '../views/accounts/CompleteView.vue'
import ArticleCreateView from '../views/articles/ArticleCreateView.vue'
import ArticleListView from '../views/articles/ArticleListView.vue'
import UserView from '../views/articles/UserView.vue'
import TutorialView from '../views/articles/TutorialView.vue'
import MyProject from '../views/articles/MyProject.vue'
import MyPage from '../views/articles/MyPage.vue'
import CreateCaption from '../views/articles/CreateCaption.vue'
Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
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
    path: '/articles/create',
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
    path: '/articles',
    name: 'ArticleListView',
    component: ArticleListView
  },
  {
    path: '/user',
    name: 'UserView',
    component: UserView
  },
  {
    path: '/tutorial',
    name: 'TutorialView',
    component: TutorialView
  },
  {
    path: '/myproject',
    name: 'MyProject',
    component: MyProject
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage
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
