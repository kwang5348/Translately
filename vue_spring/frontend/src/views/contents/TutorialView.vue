<template>
    <div class="wrapper font-ment">
      <!-- Page Content  -->
      <div class="tutorial-head">
        <img src='../../../src/img/tutorial-head.png' alt="튜토리얼 헤드" class="tutorial-head"/>
      </div>
      <b-col>
        <b-button variant="warning" class="start-button text-dark" href="/contents/translate" style="font-weight: bold; font-size: 21px;">
          지금 시작하기
        </b-button>
      </b-col>
      <b-col style="margin-top: -110px;">
        <img src='../../../src/img/tutorial-exp1.png' alt="튜토리얼 바디" class="tutorial-body"/>
        <img src='../../../src/img/tutorial-exp2.png' alt="튜토리얼 바디" class="tutorial-body"/>
      </b-col>
      <b-col>
        <b-button variant="warning" class="mb-5 text-dark" href="/contents/translate" style="font-weight: bold; font-size: 21px;">
          지금 시작하기
        </b-button>
      </b-col>
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

.tutorial-body {
  width: 720px;
}

.start-button {
  margin-top: -250px;
}

.v-application a {
    color: black;
}

.font-ment {
  font-family: 'InfinitySans-RegularA1';
}
</style>