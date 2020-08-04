<template>
<div class="wrapper">
  <!-- Sidebar  -->
  <!-- <div class="article-list-view"> -->
    <nav id="sidebar">
            <div class="sidebar-header">
                <button type="button" id="sidebarCollapse" class="btn">
                    <i class="fa fa-bars fa-inverse"></i>
                </button>
            </div>

            <ul class="list-unstyled components">
                <li>
                    <a href="/tutorial">
                    <!-- <a href="/tutorial" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle"> -->
                        <i class="fas fa-home"></i>
                        튜토리얼
                    </a>
                </li>
                <li>
                    <a href="/myproject">
                        <i class="fas fa-briefcase"></i>
                        내 프로젝트
                    </a>
                </li>
                <li class="active">
                    <a href="/articles">
                        <i class="fas fa-image"></i>
                        커뮤니티
                    </a>
                </li>
                <li>
                    <a href="/mypage">
                        <i class="fas fa-question"></i>
                        마이 페이지
                    </a>
                </li>
            </ul>

            <ul class="list-unstyled CTAs">
                <li>
                    <a href="https://bootstrapious.com/tutorial/files/sidebar.zip" class="download">Download Tutorial PDF</a>
                </li>
                <li>
                    <a href="/" class="article">Back To HOME</a>
                </li>
            </ul>
            <ul class="list-unstyled components">
                <li>
                    <a href="#">
                        <span><i class="fa fa-power-off"></i></span>
                        <span><router-link @click.native="logout" to="/">로그아웃</router-link></span>
                    </a>
                </li>
            </ul>
        </nav>

        <!-- Page Content  -->
        <div class="img mb-5" style="margin-top: 70px; margin-left: 300px">
          <img src='../../../src/img/prepare.png' alt="준비중 로고" height="300"/>
          <h4>다른 사람들이 만든 자막을 볼 수 있습니다.</h4>
          <form class="form-inline d-flex justify-content-center">
            <input class="form-control my-3" type="search" style="width: 600px" placeholder="자막 검색" aria-label="Search" id="subtitle-search">
            <button class="btn btn-secondary" type="submit" id="subtitle-button">Enter</button>
          </form>
          <table class="table">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Title</th>
                <th scope="col">작성자</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="article in articles" :key="article.id">
                <th scope="row">{{ article.id }}</th>
                <td>{{ article.title }}</td>
                <td>{{ article.user.username }}</td>
              </tr>
            </tbody>
          </table>
        </div>
  </div>
</template>

<script>
import axios from 'axios'

const SERVER_URL = 'http://127.0.0.1:8000'

export default {
  name: 'ArticleListView',
  data() {
    return {
      articles: []
    }
  },
  methods: {
    getArticles() {
      axios.get(SERVER_URL + '/articles/')
        .then(res => {
          this.articles = res.data
        })
        .catch(err => console.log(err))
    }
  },
  created() {
    this.getArticles()
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
