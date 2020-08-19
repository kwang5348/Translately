<template>
  <div class="whole font">
  <Navbar1 />
  <!-- <p style="margin-top: 50px; font-size: 30px; color: white; font-weight: bolder">자주 묻는 질문</p> -->
  <v-card max-width="1000" class="mx-auto" style="margin-top: 60px;" >
    <v-toolbar color="deep-purple darken-1" dark>
      <!-- <v-app-bar-nav-icon></v-app-bar-nav-icon> -->
      <v-toolbar-title>공지사항</v-toolbar-title>
      <v-spacer></v-spacer>
      <!-- <v-btn icon><v-icon>mdi-dots-vertical</v-icon></v-btn> -->
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
    data () {
      return {
        items: [
          {
            title: '[문의사항]  문희는 호박고구마가 먹고싶어요',
            items: [
              { title: '호박고구마!!!!!!!!!!!!!!!!!' },
            ],
          },
          {
            title: '[문의사항]  판매자에게 연락하고 싶어요.',
            active: true,
            items: [
              { title: '판매자와 전화연결이 되지 않는다면 상품 상세페이지에서 판매자에게 문의하기로 글을 남겨 주세요.'},
            ],
          },
          {
            title: '[코인]  충전 안내사항',
            items: [
              { title: '추후 제공하겠습니다. 회원가입 시 제공하는 600코인 으로 서비스를 즐겨보세요.' },
            ],
          },
          {
            title: '[코인]  이용안내',
            items: [
              { title: '코인이 부족하면 자막을 제공받으실 수 없습니다.' },
            ],
          },
          {
            title: '[서비스]  YOUTUBE 영상 번역',
            items: [
              { title: '1. 올바른 YOUTUBE 영상 링크를 입력하세요.' },
              { title: '2. 영상언어와 자막언어를 선택 후, 영상업로드 버튼을 누르세요.' },
              { title: '3. 생성된 자막을 수정할 수 있습니다.' },
              { title: '4. 자막 다운버튼을 눌러 다운로드 받으세요.' },
            ],
          },
          {
            title: '[서비스]  LOCAL 영상 번역',
            items: [
              { title: '1. 파일 선택방식 또는 마우스로 파일을 끌어서 번역하실 파일을 선택하세요.' },
              { title: '2. 영상언어와 자막언어를 선택 후, 영상업로드 버튼을 누르세요.' },
              { title: '3. 생성된 자막을 수정할 수 있습니다.' },
              { title: '4. 자막 다운버튼을 눌러 다운로드 받으세요.' },
            ],
          },
          {
            // action: 'local_offer',
            title: 'Promotions',
            items: [
              { title: 'List Item' },
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

</style>