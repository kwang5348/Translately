<template>
  <div class="whole font">
  <Navbar1 />
  <!-- <p style="margin-top: 50px; font-size: 30px; color: white; font-weight: bolder">자주 묻는 질문</p> -->
  <v-card max-width="1000" class="mx-auto" style="margin-top: 60px;" >
    <v-toolbar color="deep-purple darken-1" dark>
      <!-- <v-app-bar-nav-icon></v-app-bar-nav-icon> -->
      <v-toolbar-title>자주 묻는 질문</v-toolbar-title>
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
          <v-list-item-content>
            <v-list-item-title v-text="item.title"></v-list-item-title>
          </v-list-item-content>
        </template>

        <v-list-item
          v-for="subItem in item.items"
          :key="subItem.title"
        >
          <v-list-item-content>
            <v-list-item-title v-text="subItem.title"></v-list-item-title>
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
            action: '상품/상품평',
            title: '판매자에게 연락하고 싶어요.',
            active: true,
            items: [
              { title: '판매자와 전화연결이 되지 않는다면 상품 상세페이지에서 판매자에게 문의하기로 글을 남겨 주세요. 1. 상품 상세정보 페이지 > 교환/반품 탭에서 확인' },
            ],
          },
          {
            action: 'restaurant',
            title: 'Dining',
            items: [
              { title: 'Breakfast & brunch' },
            ],
          },
          {
            action: 'school',
            title: 'Education',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'directions_run',
            title: 'Family',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'healing',
            title: 'Health',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'content_cut',
            title: 'Office',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'local_offer',
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

<style>

.whole {
  background-image: url('../img/bg-faq.png');
  height: 850px;
}

.font {
  font-family: 'InfinitySans-RegularA1'
}

</style>