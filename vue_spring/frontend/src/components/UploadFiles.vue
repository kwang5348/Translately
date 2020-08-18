<template>
  <div @drop.prevent="addFile" @dragover.prevent class="font">
    <div class="container">
      <img src=".\..\img\box-upload.png" alt="업로드 박스" class="upload-box">
    </div>
    <div class="upload-content">
      <label class="selectbtn btn btn-default font" style="position: absolute; margin: 15px 0px 0px -220px;">
        <!-- <input type="file"  /> -->
        <input type="text" :value="fileName" id="fileName" class="file_input_textbox" readonly="readonly">
        <input type="button" value="파일 선택" class="file_input_button font-weight-bold"/>
        <input type="file" ref="file" @change="selectFile" class="file_input_hidden" onchange="javascript:document.getElementById('fileName').value = this.value.split('\\')[this.value.split('\\').length-1]">
      </label>
      <!-- :disabled="!selectedFiles" -->
      <div class="formodal">
        <b-button v-b-modal.show-btn class="uploadbtn btn font" 
        @click="$bvModal.show('bv-modal-example')" style="margin: 70px 0px 0px -70px">
          자막 생성 시작
        </b-button>
        <!-- 모달 창 -->
        <b-modal id="bv-modal-example" hide-footer>
          <!-- 헤더 -->
          <template v-slot:modal-title class="font">언어 선택</template>
          <p style="font-size: 15px;" class="font mb-3">해당 영상의 언어와 자막으로 번역하려는 언어를 설정해주세요.</p>
          <div class="d-flex justify-content-around font">
            <p><b-button variant="light" size="lg" class="font" quared v-b-popover.hover.top="'해당 영상의 언어를 선택해주세요'" title="음성 언어" style="font-weight: bold;">
              음성</b-button></p>
            <p><b-button variant="light" size="lg" squared v-b-popover.hover.top="'자막으로 번역하려는 언어를 선택해주세요'" title="자막 언어" style="font-weight: bold;">
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
            <b-button block squared variant="primary" class="mt-3" @click="uploadOption" style="font-weight: bolder; font-size: 17px">영상 업로드</b-button>
            <!-- <b-button squared class="mt-3" @click="$bvModal.hide('bv-modal-example')">취소</b-button> -->
          </div>
          <hr>
          <!-- <p class="text-center" style="color: black;">영상을 번역하고 있습니다.</p> -->
          <div v-if="currentFile" class="progress">
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
          </div>
        </b-modal>

      </div>
    </div>

  </div>
</template>

<script>
import http from "../http-common"

export default {
  name: "upload-files",
  data() {
    return {
      selectedFiles: undefined,
      currentFile: undefined,
      progress: 0,
      message: "",
      link: "",
      fileInfos: [],
      fileName: "선택된 파일이 없습니다.",

      // 모달 관련
      uploadData: {
        video_name: "",
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
          { value: 'jp', text: '일본어' },


        ],
        option2: [
          { value: null, text: '선택해주세요' },
          { value: 'en', text: '영어' },
          { value: 'ko', text: '한국어' },
          { value: 'ru', text: '러시아어' },
          { value: 'jp', text: '일본어' },
        ]
          }
      }
  },
  computed: {
    LinkState() {
      return this.link.length >2 ? true : false
    }
  },
  methods: {
    upload(file, onUploadProgress) {
      const SERVER_URL = 'http://i3a511.p.ssafy.io:8399'
      let formData = new FormData();

      formData.append("file", file);

      return http.post(`${SERVER_URL}/api/wav/upload`, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          "jwt-auth-token": this.$cookies.get("auth-token")
        },
        onUploadProgress
      })
    },
    selectFile() {
      console.log("exex")
      this.selectedFiles = this.$refs.file.files
    },
    addFile(e) {
      this.selectedFiles = e.dataTransfer.files;
      this.fileName = e.dataTransfer.files[0].name

    },
    uploadOption() {
      console.log("start")
      this.progress = 0;
      this.currentFile = this.selectedFiles.item(0);
      this.$emit('upload-file', this.currentFile)
      this.upload(this.currentFile, event => {
        this.progress = Math.round((100 * event.loaded) / event.total);
      })
        .then(response => {
          this.uploadData.video_name = this.selectedFiles.item(0).name.replace(".mp4", "")
          this.uploadData.subtitle_file= this.selectedFiles.item(0).name.replace(".mp4", "")
          this.message = response.data.message;
          this.$emit('submit-upload-option', this.uploadData)
          this.$router.push('/contents/createcaption')
          // return UploadService.getFiles();
        })
        .catch(() => {
          this.progress = 0;
          this.message = "Could not upload the file!";
          this.currentFile = undefined;
        });
    }
  },
  // mounted() {
  //   UploadService.getFiles().then(response => {
  //     this.fileInfos = response.data;
  //   });
  // }
}
</script>


<style scoped>
.img-video {
  position: absolute;
  margin-top: -255px;
  margin-left: 1460px;
}

.selectbtn {
  position: absolute;
  margin-top: -150px;
  margin-left: 1350px;
}

.uploadbtn {
  position: absolute;
  background-color: #564892;
  color: white;
  border-color: transparent;
}

b-button {
  font-weight: bold;
}

.file_input_textbox{width: 320px;}
.file_input_div{position:relative;width:100px;height:23px;overflow:hidden; }
.file_input_button{width:100px;position:absolute; margin-left:3px; top:6px; background-color:rgb(134, 134, 134); color:#fff;}
.file_input_hidden{font-size:45px; width:100px; position:absolute; top:0px; opacity:0; 

filter:alpha(opacity=0); 
-ms-filter:"alpha(opacity=0)"; 
-khtml-opacity:0; 
-moz-opacity:0;}


.upload-box {
  max-width: 500px 
}

/* .upload-content {
  margin-top: 500px;
  margin-left: -1100px;
} */

.font {
  margin-bottom: 0px;
  font-family: 'InfinitySans-RegularA1'
}

.file_input_textbox {
  height: 35px;
  background-color: rgb(238, 238, 238);
  padding-left: 15px;
}

.file_input_button{
  height: 35px;
}

</style>