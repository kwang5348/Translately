<template>
  <div class="wrapper">
    <!-- Page Content  -->
    <div class="img mb-5 font-ment" style="margin-top: 50px;">
      <!-- <div class="my-2" style="text-align: right; margin-right: 50px;">
        <span><v-btn color="warning" dark><v-icon class="mr-2">mdi-account-circle</v-icon><b>내가 만든 자막</b></v-btn></span>
      </div> -->
      <p class="mb-5" style="font-size: 20px; font-weight: bold;">다른 사람들이 만든 자막을 키워드로 검색해보세요.</p>
      <v-toolbar color="transparent" dark class="mx-auto" max-width="1200">
      <v-text-field class="mx-4" flat hide-details label="Search" v-model="searchdata" solo-inverted></v-text-field>
      <v-btn icon color="grey darken-3" @click="getsearch"><v-icon>mdi-magnify</v-icon></v-btn>
      </v-toolbar>

      <div class="row mt-5 mb-5">
        <div class="col-2"></div>
        <div class="col-4"><p style="font-weight: bolder; font-size: 60px; margin-bottom: 2px">1000</p>
          <p>오늘까지 만들어진 자막</p>
        </div>
        <div class="col-4">
          <p style="font-weight: bolder; font-size: 60px; margin-bottom: 2px">510</p>
          <p>사용 중인 유저</p>
        </div>
        <div class="col-2 row align-items-end">
          <p style="margin-bottom: 0; font-weight: bolder; color: gray">언어</p>
          <b-badge pill variant="primary" style="font-size: 16px; margin: 0px 2px 0px 4px;">음성</b-badge>
          <b-badge pill variant="warning" style="font-size: 16px; margin: 0px 2px;">자막</b-badge>  
        </div>
      </div>
      <v-tabs centered>
        <v-tab @click="getSub" style="font-size: 17px">전체보기</v-tab>
        <v-tab @click="getMy" style="font-size: 17px">내 자막 보기</v-tab>
      </v-tabs>
      <!-- 카드 -->
      <v-card class="mx-auto" max-width="1150">
        <!-- <v-toolbar color="indigo" dark><v-btn icon><v-icon>mdi-magnify</v-icon></v-btn></v-toolbar> -->
        <div class="text-center">
          <v-row dense>
            <v-col v-for="(data, index) in calData" :key="index" cols="4">
              <v-card>
                <v-img src="http://i3a511.p.ssafy.io/api/jpg/download?fileLink={{ data.object.subtitle_file }}" class="white--text align-end m-1"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="250px">
                  <v-card-title v-text="data.video_name"></v-card-title>
                </v-img>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <b-badge pill variant="primary" style="font-size: 17px; margin: 0px 2px;">{{ data.start_sub_code }}</b-badge>
                  <b-badge pill variant="warning" style="font-size: 17px; margin: 0px 2px;">{{ data.target_sub_code }}</b-badge>
                  <!-- <v-btn icon><v-icon>mdi-bookmark</v-icon></v-btn> -->
                  <v-btn icon><v-icon>mdi-open-in-new</v-icon></v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
          <v-pagination
            v-model="curPageNum"
            :length="numOfPages"
            prev-icon="mdi-menu-left"
            next-icon="mdi-menu-right"
          ></v-pagination>
        </div>
      </v-card>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

const SERVER_URL = 'http://i3a511.p.ssafy.io'

export default {
  name: 'Community',
  data() {
    return {
      subtitles: [],
      thumbnailUrl: "",
      // mysubs: [],
      searchdata:'',
      listData: [],
      dataPerPage:6,
      curPageNum:1,
    }
  },
  methods: {  
    getsearch() {
      axios.get(`${SERVER_URL}/api/subtitle/search?keyword=${this.searchdata}`, {
        headers: {
          "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
      .then(response => {
        this.subtitles = response.data.object
        console.log(this.subtitles)
        this.searchdata = ''
      })
    }, 
    getSub() {
      axios.get(`${SERVER_URL}/api/subtitle/selectAll?input=3816`, {
        headers: {
          "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
      .then(response => {
        console.log(response)
        this.subtitles = response.data.object
        this.thumbnailUrl = "http://i3a511.p.ssafy.io/api/jpg/download?fileLink=" + response.data.object.subtitle_file
        this.listData = this.subtitles
        console.log(this.subtitles)
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
        console.log(this.subtitles)
        
      })
    
    },
    // delete_data() {
    //   axios.get(`${SERVER_URL}/api/subtitle/delete?subid=${this.subtitles[index].subid})
    // }
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
