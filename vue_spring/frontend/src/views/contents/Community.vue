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
          <th scope="col">Title</th>
          <th scope="col">영상 제목</th>
          <th scope="col">썸네일</th>
          <th scope="col">영상 언어</th>
          <th scope="col">자막 언어</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="subtitle in subtitles" :key="subtitle.id">
          <th scope="row">{{ subtitle.subid }}</th>
          <td>{{ subtitle.video_name }}</td>
          <td>{{ subtitle.thumbnail }}</td>
          <td>{{ subtitle.start_sub_code }}</td>
          <td>{{ subtitle.target_sub_code }}</td>

        </tr>
      </tbody>
    </table>
  </div>
  </div>
</template>

<script>
  import axios from 'axios'

  const SERVER_URL = 'http://i3a511.p.ssafy.io'

export default {
  name: 'ArticleListView',
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
        axios.get(SERVER_URL + '/api/subtitle/selectAll?input=3816')
          .then(res => {
            this.sub = res.data
            console.log(res.data)
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
