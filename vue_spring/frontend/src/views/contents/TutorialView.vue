<template>
    <div class="wrapper font-ment">
        <!-- Page Content  -->
        <div class="tutorial-head">
            <img src='../../../src/img/tutorial-head.png' alt="튜토리얼 헤드" class="tutorial-head"/>
        </div>
        <b-button variant="warning" class="start-button" href="/contents/translate" style="font-weight: bold; font-size: 21px;">
          지금 시작하기
        </b-button>
        <div class="img mb-5" style="margin-top: 210px; margin-left: -580px">
        </div>
        
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

.v-application a {
    color: black;
}

.font-ment {
  font-family: 'InfinitySans-RegularA1';
}
</style>