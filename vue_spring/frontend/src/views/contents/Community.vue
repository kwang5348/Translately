<template>
<div class="wrapper">
  <!-- Page Content  -->
  <div class="img mb-5" style="margin-top: 70px;">
    <p style="font-size: 20px; font-weight: bold;">다른 사람들이 만든 자막을 키워드로 검색해보세요.</p>
    <form class="form-inline d-flex justify-content-center">
      <input class="form-control my-3" type="search" style="width: 600px" placeholder="자막 검색" aria-label="Search" id="subtitle-search">
      <button class="btn btn-secondary" type="submit" id="subtitle-button" :click="getSub">
        Enter
      </button>
    </form>
    <div class="row mt-3">
      <div class="col-2"></div>
      <div class="col-4"><p style="font-size: 60px;">1000</p>
        <p>오늘 만들어진 자막</p>
      </div>
      <div class="col-4">
        <p style="font-size: 60px;">510</p>
        <p>오늘 다운로드된 자막</p>
      </div>
      <div class="col-2"></div>
    </div>
    
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <!-- <th scope="col">Title</th> -->
          <th scope="col">영상 제목</th>
          <th scope="col">썸네일</th>
          <th scope="col">영상 언어</th>
          <th scope="col">자막 언어</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="subtitle in subtitles" :key="subtitle.subid">
          <th scope="row">{{ subtitle.subid }}</th>
          <td>{{ subtitle.video_name }}</td>
          <td>{{ subtitle.thumbnail }}</td>
          <td><b-badge pill variant="primary">{{ subtitle.start_sub_code }}</b-badge></td>
          <td><b-badge pill variant="warning">{{ subtitle.target_sub_code }}</b-badge></td>

        </tr>
      </tbody>
    </table>

     <v-card class="d-inline-block mx-auto">
      <v-container>
        <v-row justify="space-between">
          <v-col cols="auto">
            <v-img
              height="200"
              width="200"
              src="https://cdn.vuetifyjs.com/images/cards/store.jpg"
            ></v-img>
          </v-col>

          <v-col
            cols="auto"
            class="text-center pl-0"
          >
            <v-row
              class="flex-column ma-0 fill-height"
              justify="center"
            >
              <v-col class="px-0">
                <v-btn icon>
                  <v-icon>mdi-heart</v-icon>
                </v-btn>
              </v-col>

              <v-col class="px-0">
                <v-btn icon>
                  <v-icon>mdi-bookmark</v-icon>
                </v-btn>
              </v-col>

              <v-col class="px-0">
                <v-btn icon>
                  <v-icon>mdi-share-variant</v-icon>
                </v-btn>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-container>
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
      subtitles: []
    }
  },
  methods: {
    // getArticles() {
    //   axios.get(SERVER_URL + '/community')
    //     .then(res => {
    //       this.articles = res.data
    //     })
    //     .catch(err => console.log(err))
    // },
    getSub() {
        axios.get(`${SERVER_URL}/api/subtitle/selectAll?input=3816`, {
        headers: {
          "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
      .then(res => {
        this.subtitles = res.data.object
        // console.log(res.data)
        // console.log(this)
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

</style>
