<template>
    <div class="wrapper">
        <!-- Page Content  -->
        <div class="tutorial-head">
            <img src='../../../src/img/tutorial-head.png' alt="튜토리얼 헤드" class="tutorial-head"/>
        </div>
        <b-button variant="warning" class="start-button" href="/contents/translate" style="font-weight: bold; font-size: 21px;">
          지금 시작하기
        </b-button>
        <div class="img mb-5" style="margin-top: 210px; margin-left: -580px">
            
        </div>
        <v-app id="app">
          <v-btn color="success">Success</v-btn> 
          <v-btn color="error">Error</v-btn>
          <v-btn color="warning">Warning</v-btn>
          <v-btn color="info">Info</v-btn>
        </v-app>
        
    </div>

</template>

<script>

export default {
  name: 'TutorialView',
  data() {
    return {
      articles: []
    }
  },
}
</script>

<style>
.tutorial-head {
  width: 100%;
}

.start-button {
  margin-top: -150px;
}
</style>