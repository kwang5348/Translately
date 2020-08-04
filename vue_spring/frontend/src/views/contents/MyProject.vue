<template>
    <div class="wrapper">
        <!-- Sidebar  -->

        <!-- Page Content  -->
        <div class="img mb-5" style="margin-top: 70px; margin-left: 300px">
            <h1>나의 프로젝트를 볼 수 있습니다.</h1>
            <img src='../../../src/img/img-myproject.png' alt="마이프로젝트 임시 이미지" height="400" style="margin-top: 50px;"/>
        </div>
        
        
    </div>
</template>

<script>
  import axios from 'axios'

  const SERVER_URL = 'http://127.0.0.1:8000'

  export default {
    name: 'MyProject',
    
    methods: {
      getUser() {
        axios.get(SERVER_URL + '/project')
      },
    }
  }
</script>

<style scoped>

</style>