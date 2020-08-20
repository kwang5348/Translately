<template>
  <v-card>
    <v-img :src="thumbnailUrl"
      gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="250px">
    </v-img>
    <v-card-actions>
      <v-spacer style="text-align: left;">
        <!-- <b-badge pill style="font-size: 17px; margin: 0px 2px; background-color: #8274BA;">{{ data.start_sub_code }}</b-badge> -->
        <b-badge pill variant="warning" style="font-size: 17px; margin: 0px 2px;">{{ data.start_sub_code }}</b-badge>
        <i class="fas fa-long-arrow-alt-right" style="font-size: 15px;"></i>
        <b-badge pill style="font-size: 17px; margin: 0px 2px; background-color: #564892;">{{ data.target_sub_code }}</b-badge>
      </v-spacer>
      <div>
        <v-btn icon><a :href="downloadUrl"><i class="fa fa-download" aria-hidden="true" style="font-size: 20px; color: #696969;"></i></a></v-btn>
        <v-btn icon @click="delete_caption" class="ml-0"><i class="fa fa-trash" aria-hidden="true" style="color: black; font-size: 20px; color: #696969;"></i></v-btn>
      </div>
    </v-card-actions>
    <div>
      <v-card-title>{{ vi_name }}</v-card-title>
    </div>
  </v-card>
</template>

<script>
import axios from 'axios';

const SERVER_URL = 'http://i3a511.p.ssafy.io:8399'
export default {
  name: "community-card",
  props: {
    index: {},
    data: {},
  },
  methods: {
    delete_caption() {
      console.log("data를 찍어보자")
      console.log(this.data.subid)
      console.log(this.data.subtitle_file)
      axios.get(`${SERVER_URL}/api/subtitle/delete?subid=${this.data.subid}`, {
        headers: {
          "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
      // .then(this.$router.go())
    }
  },
  computed: {
    thumbnailUrl() {
      return "http://i3a511.p.ssafy.io/api/jpg/download?fileLink=" + this.data.subtitle_file
    },
    downloadUrl() {
      return `${SERVER_URL}/api/vtt/download?fileLink=${this.data.subtitle_file}_${this.data.start_sub_code}_${this.data.target_sub_code}`
    },
    vi_name() {
      if (this.data.video_name.length > 22) {
        const v_name = this.data.video_name.substring(0,21) + '...'
        return v_name
      } else {
        const v_name = this.data.video_name
        return v_name
      }
    }
  }
}
</script>

<style>

</style>