<template>
  <v-card>
    <v-img :src="thumbnailUrl"
      gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)" height="250px">
      <v-card-title v-text="this.data.video_name"></v-card-title>
    </v-img>
    <v-card-actions>
      <v-spacer></v-spacer>
      <b-badge pill variant="primary" style="font-size: 17px; margin: 0px 2px;">{{ data.start_sub_code }}</b-badge>
      <b-badge pill variant="warning" style="font-size: 17px; margin: 0px 2px;">{{ data.target_sub_code }}</b-badge>
      <!-- <v-btn icon><v-icon>mdi-bookmark</v-icon></v-btn> -->
      <v-btn icon><a :href="downloadUrl"><v-icon>mdi-open-in-new</v-icon></a></v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>


  const SERVER_URL = 'http://i3a511.p.ssafy.io:8399'
export default {
  name: "community-card",
  props: {
    index: {},
    data: {},
  },
  computed: {
    thumbnailUrl() {
      return "http://i3a511.p.ssafy.io/api/jpg/download?fileLink=" + this.data.subtitle_file
    },
    downloadUrl() {
      return `${SERVER_URL}/api/vtt/download?fileLink=${this.data.subtitle_file}_${this.data.start_sub_code}_${this.data.target_sub_code}`
    }
  }
}
</script>

<style>

</style>