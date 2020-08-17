<template>
    <div class="wrapper">
      <div class="img mb-5 font-ment" style="margin-top: 90px;">
        <p class="mb-5" style="font-size: 20px; font-weight: bold;">내가 만들었던 자막을 다시 볼 수 있어요.</p>
        <v-toolbar color="transparent" dark class="mx-auto" max-width="1200">
        <v-text-field class="mx-4" flat hide-details label="Search" solo-inverted :enter="getSub"></v-text-field>
        <v-btn icon color="grey darken-3" :click="getSub"><v-icon>mdi-magnify</v-icon></v-btn>
        </v-toolbar>
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

.font-ment {
  font-family: 'InfinitySans-RegularA1';
}

</style>