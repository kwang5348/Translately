<template>
  <div class="wrapper">
    <!-- Page Content  -->
    <div class="img mb-5 font-ment" style="margin-top: 90px;">
      <p class="mb-5" style="font-size: 20px; font-weight: bold;">다른 사람들이 만든 자막을 키워드로 검색해보세요.</p>
      <v-toolbar color="transparent" dark class="mx-auto" max-width="1200">
      <v-text-field class="mx-4" flat hide-details label="Search" solo-inverted :enter="getSub"></v-text-field>
      <v-btn icon color="grey darken-3" :click="getSub"><v-icon>mdi-magnify</v-icon></v-btn>
      </v-toolbar>

      <!-- <form class="form-inline d-flex justify-content-center">
        <input class="form-control my-3" type="search" style="width: 600px" placeholder="자막 검색" aria-label="Search" id="subtitle-search">
        <button class="btn btn-secondary" type="submit" id="subtitle-button" :click="getSub">
          Enter</button>
      </form> -->
      <div class="row mt-5 mb-5">
        <div class="col-2"></div>
        <div class="col-4"><p style="font-size: 60px; margin-bottom: 2px">1000</p>
          <p>오늘 만들어진 자막</p>
        </div>
        <div class="col-4">
          <p style="font-size: 60px; margin-bottom: 2px">510</p>
          <p>오늘 다운로드된 자막</p>
        </div>
        <div class="col-2"></div>
      </div>
      
      <!-- 카드 -->
      <v-card class="mx-auto" max-width="1200">
        <!-- <v-toolbar color="indigo" dark><v-btn icon><v-icon>mdi-magnify</v-icon></v-btn></v-toolbar> -->
          <v-row dense>
            <v-col v-for="subtitle in subtitles" :key="subtitle.subid" cols="4">
              <v-card>
                <v-img src="https://cdn.vuetifyjs.com/images/cards/house.jpg" class="white--text align-end m-1"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="250px">
                  <v-card-title v-text="subtitle.video_name"></v-card-title>
                </v-img>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <b-badge pill variant="primary" style="font-size: 17px; margin: 0px 2px;">{{ subtitle.start_sub_code }}</b-badge>
                  <b-badge pill variant="warning" style="font-size: 17px; margin: 0px 2px;">{{ subtitle.target_sub_code }}</b-badge>
                  <!-- <v-btn icon><v-icon>mdi-bookmark</v-icon></v-btn> -->
                  <v-btn icon><v-icon>mdi-open-in-new</v-icon></v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
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
    }
  },
  methods: {   
    getSub() {
        axios.get(`${SERVER_URL}/api/subtitle/selectAll?input=3816`, {
        headers: {
          "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
      .then(res => {
        this.subtitles = res.data.object
        console.log(this.subtitles)
      })
    },
  },
  created() {
    this.getSub()
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
