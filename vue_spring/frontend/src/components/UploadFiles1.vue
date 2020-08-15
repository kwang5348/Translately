<template>
  <div role="group">
      <label for="input-live"></label>
      <b-form-input
        id="input-live"
        v-model="fileLink"
        :state="LinkState"
        aria-describedby="input-live-help input-live-feedback"
        placeholder="예시) https://www.youtube.com/watch?v="
        trim
      ></b-form-input>

      <b-button v-if="LinkState" v-b-modal.show-btn class="uploadbtn btn btn-warning" 
        @click="$bvModal.show('bv-modal-youtube')">
          자막 생성 시작
        </b-button>
      
          <b-modal id="bv-modal-youtube" hide-footer>
          <!-- 헤더 -->
          <template v-slot:modal-title>언어 선택</template>
          <p style="font-size: 15px;">해당 영상의 언어와 자막으로 번역하려는 언어를 설정해주세요.</p>
          <div class="d-flex justify-content-around">
            <p><b-button variant="light" size="lg" squared v-b-popover.hover.top="'해당 영상의 언어를 선택해주세요'" title="음성 언어" style="font-weight: bold;">
              음성</b-button></p>
            <p><b-button variant="light" size="lg" squared v-b-popover.hover.top="'자막으로 번역하려는 언어를 선택해주세요'" title="자막 언어" style="font-weight: bold;">
              자막</b-button></p>
          </div>
          <!-- 언어 선택 -->
          <div class="d-flex justify-content-around">
            <b-form-select v-model="uploadData.start" :options="uploadData.option1"></b-form-select>
            <b-form-select v-model="uploadData.target" :options="uploadData.option2"></b-form-select>
          </div>
          <div class="d-flex justify-content-around">
            <div class="mt-3">음성 언어: <strong>{{ uploadData.start }}</strong></div>
            <div class="mt-3">자막 언어: <strong>{{ uploadData.target }}</strong></div>
          </div>
          <div class="d-flex justify-content-around">
            <!-- 링크 연결된 upload 버튼 -->
            <b-button block squared variant="primary" class="mt-3" @click="uploadOption" style="font-weight: bolder; font-size: 17px">영상 업로드</b-button>
            <!-- <b-button squared class="mt-3" @click="$bvModal.hide('bv-modal-youtube')">취소</b-button> -->
          </div>
          <hr>
          <!-- <p class="text-center" style="color: black;">영상을 번역하고 있습니다.</p> -->
          <!-- <div v-if="currentFile" class="progress">
            <div
              class="progress-bar progress-bar-info progress-bar-striped"
              role="progressbar"
              :aria-valuenow="progress"
              aria-valuemin="0"
              aria-valuemax="100"
              :style="{ width: progress + '%' }"
            >
              {{ progress }}%
            </div>
          </div> -->
        </b-modal>







      <!-- <b-form-text id="input-live-help">유투브 영상 링크를 입력해주세요.</b-form-text> -->
    </div>
</template>

<script>
import axios from 'axios';

const SERVER_URL = 'http://i3a511.p.ssafy.io:8301'

export default {
  name: "upload-files1",
  computed: {
    LinkState() {
      return this.fileLink.startsWith("https://www.youtube.com/watch?v=") ? true : false
    }
  },
  data() {
    return {
      fileLink:'',


      uploadData: {
        video_name: null,
        thumbnail: null,
        subtitle_file: "",
        youtube_url: null,
        start_sub_code: null,
        target_sub_code: null,
        duration: 0,
        option1: [
          { value: null, text: '선택해주세요' },
          { value: 'en', text: '영어' },
          { value: 'ko', text: '한국어' },
        ],
        option2: [
          { value: null, text: '선택해주세요' },
          { value: 'en', text: '영어' },
          { value: 'ko', text: '한국어' },
        ]
          }
      }
  },
  methods: {
    uploadOption() {
      console.log("start")
      console.log("유튜브 영상 파일 다운로드를 시작합니다.")
      this.$emit('upload-file', this.fileLink.replace("https://www.youtube.com/watch?v=", ""))
      axios.get(`${SERVER_URL}/api/youtube/upload?fileLink=${this.fileLink}`, {
        headers: {
          "jwt-auth-token": this.$cookies.get("auth-token")
        }
      })
      .then(response => {
        console.log("유튜브 영상 다운로드가 완료되었습니다.")
        console.log(response)
        this.uploadData.video_name = this.fileLink.replace("https://www.youtube.com/watch?v=", "")
        this.uploadData.subtitle_file = this.fileLink.replace("https://www.youtube.com/watch?v=", "")
        this.uploadData.youtube_url = this.fileLink
        this.message = response.data.message;
        this.$emit('submit-upload-option', this.uploadData)
        this.$router.push('/createcaption')
      })
    }
  },
  
    
}
    
  

</script>

<style>
.form-control {
    display: block;
    width: 50px;
}

.uploadbtn {
  margin: 10px 0px;
}

</style>