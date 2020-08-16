<template>
    <div class="wrapper">
        <!-- Sidebar  -->

        <!-- Page Content  -->
        <div class="img mb-5" style="margin-top: 70px;">
          <form class="form-inline d-flex justify-content-center">
            <input class="form-control my-3" type="search" style="width: 600px" placeholder="자막 검색" aria-label="Search" id="subtitle-search">
            <button class="btn btn-secondary" type="submit" id="subtitle-button" v-on:click="getData">
              Enter
            </button>
          </form>
        </div>
        
        
    </div>
</template>

<script>
  import axios from 'axios'

  const SERVER_URL = 'http://i3a511.p.ssafy.io'

  export default {
    name: 'MyProject',
    
    methods: {
      getData() {
        axios.get(SERVER_URL + '/api/subtitle/selectAll?input=3816')
          .then(res => {
            console.log(res)
          })
      },
    }
  }
</script>

<style scoped>
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