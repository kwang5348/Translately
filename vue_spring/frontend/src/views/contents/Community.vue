<template>
  <div class="wrapper">
    <!-- Page Content  -->
    <div class="img mb-5 font-ment" style="margin-top: 50px;">
      <p class="mb-5" style="font-size: 20px; font-weight: bold;">다른 사람들이 만든 자막을 키워드로 검색해보세요.</p>
      <v-toolbar color="transparent" dark class="mx-auto" max-width="1200">
      <v-text-field @keypress.Enter="getsearch" class="mx-4" flat hide-details label="Search" v-model="searchdata" solo-inverted></v-text-field>
      <v-btn icon color="grey darken-3" @click="getsearch"><v-icon>mdi-magnify</v-icon></v-btn>
      </v-toolbar>

      <div class="row mt-5 mb-5">
        <div class="col-2"></div>
        <div class="col-4"><p style="font-weight: bolder; font-size: 60px; margin-bottom: 2px">{{ this.contents + 1992 }}</p>
          <p>오늘까지 만들어진 자막</p>
        </div>
        <div class="col-4">
          <p style="font-weight: bolder; font-size: 60px; margin-bottom: 2px">{{ this.users + 421 }}</p>
          <p>사용 중인 유저</p>
        </div>
        <div class="col-2"></div>
      </div>
      <!-- 카드 -->
      <v-card class="mx-auto" max-width="1150">
      <div class="row mt-5">
        <div class="col-3"></div>
        <div class="col-6">
          <v-tabs centered>
            <v-tab @click="getSub" style="font-size: 17px">전체보기</v-tab>
            <v-tab @click="getMy" style="font-size: 17px">내 자막 보기</v-tab>
          </v-tabs>
        </div>
        <div class="col-3 row align-items-end mb-2" style="text-align: right; justify-content: center;">
          <b-badge pill variant="warning" style="font-size: 16px; margin: 0px 2px 0px 4px;">
            음성 언어
            </b-badge>
            <i class="fas fa-long-arrow-alt-right" style="font-size: 20px;"></i>
          <b-badge pill style="font-size: 16px; margin: 0px 2px; background-color: #564892;">자막 언어</b-badge>  
        </div>
      </div>
        <div class="text-center">
          <v-row dense>
            <community-card class="col-4" v-for="(data, index) in calData" :key="index" :data="data" :index="index" cols="4"></community-card>
          </v-row>
          <v-pagination
            v-model="curPageNum"
            :length="numOfPages"
            :total-visible="7"
            circle
          ></v-pagination>
        </div>
      </v-card>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import CommunityCard from "../../components/CommunityCard"

const SERVER_URL = 'http://i3a511.p.ssafy.io'

export default {
  name: 'Community',
  components: {
    CommunityCard,
  },
  data() {
    return {
      contents:'',
      users:'',
      subtitles: [],
      searchdata:'',
      listData: [],
      dataPerPage:6,
      curPageNum:1,
    }
  },
  methods: {
    find_count() {
      axios.get(`${SERVER_URL}/api/subtitle/userCount`, {
        headers: {
          "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
      .then(response => {
        this.contents = response.data.object.subtitleCount
        this.users = response.data.object.userCount
      })
    },  
    getsearch() {
      axios.get(`${SERVER_URL}/api/subtitle/search?keyword=${this.searchdata}`, {
        headers: {
          "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
      .then(res => {
        this.subtitles = res.data.object
        this.listData = this.subtitles
      })
    }, 
    getSub() {
      axios.get(`${SERVER_URL}/api/subtitle/selectAll?input=3816`, {
        headers: {
          "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
      .then(response => {
        this.subtitles = response.data.object
        this.thumbnailUrl = "http://i3a511.p.ssafy.io/api/jpg/download?fileLink=" + response.data.object.subtitle_file
        this.listData = this.subtitles
      })
    },
    getMy() {
      axios.get(`${SERVER_URL}:8399/api/subtitle/mylist`, {
        headers: {
          "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
      .then(res => {
        this.subtitles = res.data.object
        this.listData = this.subtitles
      })
    },
  },
  created() {
    this.find_count()
    this.getSub()
  },
  computed: {
    startOffset() {
      return ((this.curPageNum - 1) * this.dataPerPage)
    },
    endOffset() {
      return (this.startOffset + this.dataPerPage)
    },
    numOfPages() {
      return Math.ceil(this.listData.length / this.dataPerPage)
    },
    calData() {
      return this.listData.slice(this.startOffset, this.endOffset)
    }
  }
}
</script>

<style scoped>
.article-list-view {
  margin: 50px;
}

#subtitle-search {
  border-radius: 0;
  margin-right: 6px;
}

#subtitle-button {
  border-radius: 0;
  padding: 6px;
  padding-left: 8px;
  padding-right: 8px;

}

.font-ment {
  font-family: 'InfinitySans-RegularA1';
}

</style>
