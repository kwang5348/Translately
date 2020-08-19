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
              <video-player-vue :downloadUrl="downloadUrl" :video=video ></video-player-vue>

              <b-card v-if="translateBusy" title="자막 생성중.." class="mb-2 mt-5">
                <div
                  class="progress-bar progress-bar-success progress-bar-striped mt-5"
                  role="progressbar"
                  :aria-valuenow="translateProgress"
                  aria-valuemin="0"
                  aria-valuemax="100"
                  :style="{ width: translateProgress + '%' }"
                >
                  {{ translateProgress }}%
                </div>
              </b-card>
              <b-card v-else title="자막 생성 완료" class="mb-2 mt-5">
                <div
                  class="progress-bar progress-bar-success progress-bar-striped mt-5"
                  role="progressbar"
                  :aria-valuenow="translateProgress"
                  aria-valuemin="0"
                  aria-valuemax="100"
                  :style="{ width: translateProgress + '%' }"
                >
                  {{ translateProgress }}%
                </div>
              </b-card>
              <div>
                
              </div>
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
                <v-responsive
                  class="overflow-y-auto"
                  min-height="400"
                  max-height="400"
                >
              <!-- <v-virtual-scroll
              :items="items"
              :item-height="50"
              height="300"
              > -->
                <subtitle-vue 
                  v-for="(subtitle, index) in subtitles" 
                  :key="index" 
                  :subtitle="subtitle"
                  :index="index"
                  @submit-subtitle-input="changeSubtitle" />
                <hr>
                <b-overlay
                  :show="translateBusy"
                  rounded
                  opacity="0.6"
                  spinner-small
                  spinner-variant="primary"
                  class="d-inline-block"
                >
                </b-overlay>
              <!-- </v-virtual-scroll> -->
                </v-responsive>
              <!-- </div> -->
               <!-- variant="outline-primary" -->
              <b-button @click="subQueChange" style="background-color: #564892;">Button</b-button>
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
        timeout: null,
        pushData: Object,
        progress: 0,
      }
    },
    beforeDestroy() {
      this.clearTimeout()
      this.$emit('destroy-create-caption')
    },
    props: {
      subtitles: {
        type: Array,
      },
      video: { 
      },
      translateBusy: {
        type: Boolean
      },
      downloadUrl: {
        type: String
      },
      translateProgress: {}
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
      changeSubtitle(subtitleData) {
        this.subtitles[subtitleData.index].targetsub = subtitleData.subtitleInput
      },
      subQueChange() {
        console.log(this.subtitles[0].subid)
        axios.get(`http://i3a511.p.ssafy.io/api/subtitle/showtrans?subid=${this.subtitles[0].subid}`, {headers: {"jwt-auth-token": this.$cookies.get("auth-token")}})
        .then(response => {
          this.pushData = response.data.object
          this.subtitles.forEach((element, index) => {
            this.pushData.transcript[index].targetsub = element.targetsub
          })
        })
        .then(()=>{
          axios.post("http://i3a511.p.ssafy.io/api/subtitle/modify", this.pushData, {headers: {"jwt-auth-token": this.$cookies.get("auth-token")}})
          .then(res => {
            console.log(res)
          })
        })
      }
      // onHidden() {
      //   // Return focus to the button once hidden
      //   this.$refs.button.focus()
      // },
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

.b-progress-bar {
  background-color:#564892 !important;
}

.font {
  margin-bottom: 0px;
  font-family: 'InfinitySans-RegularA1'
}
</style>