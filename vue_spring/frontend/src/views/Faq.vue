<template>
  <div class="whole font">
  <Navbar1 @logout="logout" :isLogin="isLogin" />
  <v-card max-width="1000" class="mx-auto" style="margin-top: 60px;" >
    <v-toolbar color="deep-purple darken-1" dark>
      <v-toolbar-title>공지사항</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-list>
      <v-list-group
        v-for="item in items"
        :key="item.title"
        v-model="item.active"
        :prepend-icon="item.action"
        no-action
      >
        <template v-slot:activator>
          <v-list-item-content class="ml-9" style="text-align: left;">
            <v-list-item-title v-text="item.title"></v-list-item-title>
          </v-list-item-content>
        </template>
        <v-list-item
          v-for="subItem in item.items"
          :key="subItem.title"
        >
          <v-list-item-content>
            <v-list-item-title v-text="subItem.title" style="text-align: left;"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-group>
    </v-list>
  </v-card>
  </div>
</template>

<script>
import Navbar1 from "@/components/Navbar1.vue"


  export default {
    components: {
      Navbar1,
    },
    created() {
      if (this.$cookies.isKey('auth-token')) {
        this.isLogin = true
      } else {
        this.isLogin = false
      }
    },
    methods: {
      logout() {
        this.$emit('logout')
      }
    },
    data () {
      return {
        isLogin: false,
        items: [
          {
            title: '[서비스]   서비스 안내사항',
            items: [
              { title: '로그인 후 코인이 존재하는 유저에게만 번역 서비스를 제공하고 있습니다.' },
            ],
          },
          {
            title: '[서비스]   YOUTUBE 영상 번역,자막 이용방법',
            items: [
              { title: '1. 올바른 YOUTUBE 영상 링크를 입력하세요.' },
              { title: '2. 영상언어와 자막언어를 선택 후, 영상업로드 버튼을 누르세요.' },
              { title: '3. 생성된 자막을 수정할 수 있습니다.' },
              { title: '4. 자막 다운버튼을 눌러 다운로드 받으세요.' },
            ],
          },
          {
            title: '[서비스]   LOCAL 영상 번역,자막 이용방법',
            items: [
              { title: '1. 파일 선택방식 또는 마우스로 파일을 끌어서 번역하실 파일을 선택하세요.' },
              { title: '2. 영상언어와 자막언어를 선택 후, 영상업로드 버튼을 누르세요.' },
              { title: '3. 생성된 자막을 수정할 수 있습니다.' },
              { title: '4. 자막 다운버튼을 눌러 다운로드 받으세요.' },
            ],
          },
          {
            title: '[서비스]   크롬 익스텐션 이용안내',
            items: [
              { title: '빠른 시일 내에 서비스 제공 예정입니다. 기다려주세요.' },
            ],
          },
          {
            title: '[코인]   코인충전 안내사항',
            items: [
              { title: '추후 제공하겠습니다. 회원가입 시 제공하는 600코인 으로 서비스를 즐겨보세요.' },
            ],
          },
          {
            title: '[코인]   코인이용안내',
            items: [
              { title: '코인이 부족하면 자막을 제공받으실 수 없습니다.' },
            ],
          },
          {
            title: '[문의사항]   900MB 동영상 업로드가 안돼요.',
            items: [
              { title: '동영상 업로드 시 최대 500MB의 영상까지만 제공하고 있습니다.' },
            ],
          },
          {
            title: '[문의사항]   Translately 서비스 이용 시 문의사항은 이렇게 이용하세요.',
            active: true,
            items: [
              { title: '홈페이지 하단에 연락처 혹은 이메일로 문의해주시면 친절하게 답변해드리겠습니다.'},
            ],
          },
        ],
      }
    },
  }
</script>

<style scoped>

.whole {
  background-image: url('../img/bg-faq.png');
  height: 850px;
}

.font {
  font-family: 'InfinitySans-RegularA1'
}

.v-icon {
  font-family: 'InfinitySans-RegularA1'
}

</style>