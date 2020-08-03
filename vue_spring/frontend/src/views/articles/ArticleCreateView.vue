<template>
  <div class="article-create-view">
    <div class="form-group">
      <label for="title">Title</label>
      <input type="text" class="form-control" v-model="article.title" id="title">
      <small id="emailHelp" class="form-text text-muted">영화와 관련된 자유로운 의견을 남겨주세요.</small>
    </div>
    <div class="form-group">
      <label for="content">Content</label>
      <textarea class="form-control" v-model="article.content" id="content" rows="10"></textarea>
    </div>
    <button class="btn btn-primary" @click="createArticle">작성하기</button>
  </div>
</template>

<script>
import axios from 'axios'
const SERVER_URL = 'http://127.0.0.1:8000'

export default {
  name: 'ArticleCreateView',
  data() {
    return {
      article: {
        title: null,
        content: null,
      }
    }
  },
  methods: {
    createArticle() {
      const config = {
        headers: {
          Authorization: `Token ${this.$cookies.get('auth-token')}`      
        }
      }
      console.log(this.article)
      axios.post(SERVER_URL + '/articles/create/', this.article, config)
        .then(() => this.$router.push('/'))
        .catch(err => console.log(err.response))
    }
  }
}
</script>

<style scoped>
.article-create-view {
  margin: 50px;
  text-align: left;
}
</style>
