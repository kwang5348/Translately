<template>
  <div class="wrapper font">
    <!-- Sidebar  -->
    <div class="container-fluid m-0 p-0" style="height:100vh;">
      <!-- Page Content  -->
      <div class="container-fluid mt-5">
        <div class="row justify-content-around" style="font-size: 25px; color: black;">
          <div class="col-5">
            <p class="font-weight-bolder" style="font-size: 25px; color: black;">영상 미리보기</p>
          </div>
          <div class="col-7">
            <p class="font-weight-bolder" style="font-size: 25px; color: black;">자막 편집 창</p>
          </div>
          <!-- <div class="col-1"></div> -->
        </div>
          <div class="row">
            <div class="col-5" style="border:1px solid transparent;">
                <!-- <p>영상</p> -->
                <video-player-vue :video=video ></video-player-vue>
            </div>
            <div class="col-7" style="border:1px solid transparent; padding-right: 10px">
              <!-- <p>자막</p> -->
              <b-alert show dismissible fade variant="warning" style="margin-top:2px; padding-left: 20px; padding-bottom: 0px;">
                <div class="row">
                  <div class="col-10" style="padding: 0px; padding-left: 20px">
                    <p style="color: gray; font-size: 13px; margin:0px">원래 음성의 자막이 표시됩니다.</p>
                    <p style="color: black; font-weight: bold; margin:0px; font-size: 13px;">이 곳에서 자막을 수정할 수 있습니다.</p>
                  </div>
                  <div class="col-1" style="padding: 0px; padding-left: 10px;">
                    <p style="font-size: 13px; margin-bottom: 0px; color: black;">시작 ~</p>
                    <p style="font-size: 13px; margin-bottom: 0px; color: black;">종료</p>
                  </div>
                </div>
              </b-alert>
              <!-- <div style="height: 550px; overflow-y: scroll;"> -->
                <b-overlay
                  :show="translateBusy"
                  rounded
                  opacity="0.6"
                  spinner-small
                  spinner-variant="primary"
                  class="d-inline-block"
                >
                </b-overlay>

                <v-responsive
                  class="overflow-y-auto"
                  max-height="400"
                >
                  <!-- <div class="pa-6 text-center">
                    Scroll down
                  </div>

                  <v-responsive
                    height="200vh"
                    class="text-center pa-2"
                  >
                    <v-responsive min-height="50vh"></v-responsive>
                    <div class="text-center body-2 mb-12">The card will appear below:</div>

                    <v-lazy
                      v-model="isActive"
                      :options="{
                        threshold: .5
                      }"
                      min-height="200"
                      transition="fade-transition"
                    >
                      <v-card
                        class="mx-auto"
                        max-width="336"
                      >
                        <v-card-title>Card title</v-card-title>

                        <v-card-text>
                          Phasellus magna. Quisque rutrum. Nunc egestas, augue at pellentesque laoreet, felis eros vehicula leo, at malesuada velit leo quis pede. Aliquam lobortis. Quisque libero metus, condimentum nec, tempor a, commodo mollis, magna.

                          In turpis. In dui magna, posuere eget, vestibulum et, tempor auctor, justo. In turpis. Pellentesque dapibus hendrerit tortor. Ut varius tincidunt libero.
                        </v-card-text>
                      </v-card>
                    </v-lazy>
                  </v-responsive> -->
                <subtitle-vue v-for="subtitle in subtitles" :key="subtitle.startTime" :subtitle="subtitle" />
                </v-responsive>

              <!-- </div> -->
            </div>
          </div>
        </div>
      </div>
    </div>
    
</template>

<script>
  import VideoPlayerVue from "./../../components/VideoPlayerVue"
  import axios from 'axios'
  import subtitleVue from './../../components/Subtitle.vue'

  const SERVER_URL = 'http://i3a511.p.ssafy.io/'

  export default {
    name: 'CreateCaption',
    components: {
      subtitleVue,
      VideoPlayerVue,
    },
    data() {
      return {
        timeout: null
      }
    },
    beforeDestroy() {
      this.clearTimeout()
    },
    props: {
      subtitles: {
        type: Array,
      },
      video: { 
      },
      translateBusy: {
        type: Boolean
      }
    },
    methods: {
      getUser() {
        axios.get(SERVER_URL + '/createcaption')
      },
      clearTimeout() {
        if (this.timeout) {
          clearTimeout(this.timeout)
          this.timeout = null
        }
      },
      setTimeout(callback) {
        this.clearTimeout()
        this.timeout = setTimeout(() => {
          this.clearTimeout()
          callback()
        }, 5000)
      },
      // onHidden() {
      //   // Return focus to the button once hidden
      //   this.$refs.button.focus()
      // },
      logout() {
        this.$cookies.remove('auth-token')
        this.isLogin = false
        this.navbar = true
        this.$router.push({name: "Home"})
      },
    },
  }

</script>

<style scoped>
.list-group-item:first-child{
  border-top-left-radius:0rem;
  border-top-right-radius:0rem;
  }
.list-group-item:last-child{
  border-bottom-right-radius:0rem;
  border-bottom-left-radius:0rem;
  }

.list-group-item {
  font-size: 20px;
  font-weight: bold;
  background-color:#564892;
  padding: 0px;
  border-color: transparent;
}

.list-group-item2 {
  font-weight: bold;
  /* background-color: black; */
}

.font {
  margin-bottom: 0px;
  font-family: 'InfinitySans-RegularA1'
}
</style>