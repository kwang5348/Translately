<template>
  <div role="group" class="font">
    <div class="container">
      <img src=".\..\img\box-youtube.png" alt="유튜브 박스" class="upload-box">
    </div>
    <label for="input-live"></label>
    <b-form-input
      id="input-live"
      v-model="fileLink"
      :state="LinkState"
      aria-describedby="input-live-help input-live-feedback"
      placeholder="예시) https://www.youtube.com/watch?v="
      trim
    ></b-form-input>

    <b-button variant="danger" v-b-modal.show-btn class="uploadbtn btn font"
      @click="$bvModal.show('bv-modal-youtube')">
        자막 생성 시작
      </b-button>
    
        <b-modal id="bv-modal-youtube" hide-footer>
        <!-- 헤더 -->
        <template v-slot:modal-title class="font">언어 선택</template>
        <p style="font-size: 15px;" class="font mb-3">해당 영상의 언어와 자막으로 번역하려는 언어를 설정해주세요.</p>
        <div class="d-flex justify-content-around">
          <p><b-button variant="light" size="lg" class="font" squared v-b-popover.hover.top="'해당 영상의 언어를 선택해주세요'" title="음성 언어" style="font-weight: bold;">
            음성</b-button></p>
          <p><b-button variant="light" size="lg" class="font" squared v-b-popover.hover.top="'자막으로 번역하려는 언어를 선택해주세요'" title="자막 언어" style="font-weight: bold;">
            자막</b-button></p>
        </div>
        <!-- 언어 선택 -->
        <div class="d-flex justify-content-around font">
          <b-form-select v-model="uploadData.start_sub_code" :options="uploadData.option1"></b-form-select>
          <b-form-select v-model="uploadData.target_sub_code" :options="uploadData.option2"></b-form-select>
        </div>
        <div class="d-flex justify-content-around font">
          <div class="mt-3">음성 언어: <strong>{{ uploadData.start_sub_code }}</strong></div>
          <div class="mt-3">자막 언어: <strong>{{ uploadData.target_sub_code }}</strong></div>
        </div>
        <div class="d-flex justify-content-around font">
          <!-- 링크 연결된 upload 버튼 -->
          <b-button v-if="busy" block squared variant="primary" class="mt-3" @click="uploadOption" style="font-weight: bolder; font-size: 17px">
            <b-spinner small type="grow"></b-spinner>
            Loading...
          </b-button>
          <b-button v-else block squared variant="primary" class="mt-3" @click="uploadOption" style="font-weight: bolder; font-size: 17px">영상 업로드</b-button>
        </div>
        
        <hr>
      </b-modal>
    </div>
</template>

<script>
import axios from 'axios';

const SERVER_URL = 'http://i3a511.p.ssafy.io:8399'

export default {
  name: "upload-files1",
  computed: {
    LinkState() {
      return this.fileLink.startsWith("https://www.youtube.com/watch?v=") ? true : false
    }
  },
  data() {
    return {
      subtitleData: {
        subtitles: [],
        subtitleName: "",
        start_sub_code: "",
        target_sub_code: "",
      },
      fileLink: '',
      busy: false,
      flag: true,
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
          { value: 'ru', text: '러시아어' },
          { value: 'ja', text: '일본어' },
        ],
        option2: [
          { value: null, text: '선택해주세요' },
          { value: 'en', text: '영어' },
          { value: 'ko', text: '한국어' },
          { value: 'ru', text: '러시아어' },
          { value: 'ja', text: '일본어' },
        ]
          }
      }
  },
  methods: {
    uploadOption() {
      if ((this.uploadData.start_sub_code != 'ko')&&(this.uploadData.target_sub_code != 'ko')){
        alert("음성언어나 번역언어 중 하나를 한국어로 지정해주세요.")
      } else if ((this.uploadData.start_sub_code === 'ko')&&(this.uploadData.target_sub_code === 'ko')) {
        alert("다른 언어를 선택해주세요.")
      } else {
        axios.get(`${SERVER_URL}/api/wav/youtubeAble?fileLink=${this.fileLink}`, {headers: {"jwt-auth-token": this.$cookies.get("auth-token")}})
        .then(res => {
          if(res.data.status) {
            const video_name = res.data.object.fileName
            if(this.LinkState){
              this.busy = true
              const fileName = this.fileLink.replace("https://www.youtube.com/watch?v=","")
              this.$emit('upload-file', fileName, "")
              axios.get(`${SERVER_URL}/api/wav/youtubeCheck?fileName=${fileName}&start_sub_code=${this.uploadData.start_sub_code}&target_sub_code=${this.uploadData.target_sub_code}`, {headers: {"jwt-auth-token": this.$cookies.get("auth-token")}})
              .then(response => {
                if(response.data.status) {
                  this.subtitleData.subtitles = response.data.object.transcript
                  this.subtitleData.subtitleName = fileName
                  this.subtitleData.start_sub_code = this.uploadData.start_sub_code
                  this.subtitleData.target_sub_code = this.uploadData.target_sub_code
                  this.$emit('submit-complete-translate', this.subtitleData)
                } else {
                  console.log("유튜브 영상 파일 다운로드를 시작합니다.")
                  axios.get(`${SERVER_URL}/api/youtube/upload?fileLink=${this.fileLink}`, {
                    headers: {
                      "jwt-auth-token": this.$cookies.get("auth-token")
                    }
                  })
                  .then(response => {
                    if(response.data.status) {
                      console.log("유튜브 영상 다운로드가 완료되었습니다.")
                      this.uploadData.video_name = video_name
                      this.uploadData.subtitle_file = this.fileLink.replace("https://www.youtube.com/watch?v=", "")
                      this.uploadData.youtube_url = this.fileLink
                      this.$emit('submit-upload-option', this.uploadData)
                    } else {
                      alert(response.data.data)
                      this.busy = false
                    }
                  })
                }
              }).catch(err => {
                console.log(err)
              }) 
            }
          } else {
            alert(res.data.data)
          }
        })
      }
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
  background-color: #564892;
  color: white;
  border-color: transparent;
}

.font {
  margin-bottom: 0px;
  font-family: 'InfinitySans-RegularA1'
}

#input-live {
  display: block;
  max-width: 480px;
  margin: 0 auto;
}

.upload-box {
  max-width: 500px 
}
</style>