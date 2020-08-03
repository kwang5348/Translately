<template>
  <div>
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
    <img class="img-video" src='../img/icon-video.png' alt="비디오 로고" height="100"/>
    <label class="selectbtn btn btn-default">
      <!-- <input type="file"  /> -->
      <input type="text" value="선택된 파일이 없습니다." id="fileName" class="file_input_textbox" readonly="readonly">
      <input type="button" value="파일 선택" class="file_input_button font-weight-bold"/>
      <input type="file" ref="file" @change="selectFile" class="file_input_hidden" onchange="javascript:document.getElementById('fileName').value = this.value.split('\\')[this.value.split('\\').length-1]">
    </label>
    <!-- :disabled="!selectedFiles" -->
    <div>
      <b-button v-b-modal.show-btn class="uploadbtn btn btn-dark" @click="$bvModal.show('bv-modal-example')">
        자막 변환
      </b-button>
      <!-- 모달 창 -->
      <b-modal id="bv-modal-example" hide-footer>
        <!-- 헤더 -->
        <template v-slot:modal-title>언어 선택</template>
        <p>음성 인식할 언어와 자막 언어를 설정해주세요.</p>
        <div class="d-flex justify-content-around">
          <p><b-button variant="outline-dark" v-b-popover="'영상의 음성 언어를 선택해주세요'" title="음성 언어">음성</b-button></p>
          <p><b-button variant="outline-dark" v-b-popover="'변환하고 싶은 자막 언어를 선택해주세요'" title="자막 언어">자막</b-button></p>
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
        <hr>
        <div class="d-flex justify-content-around">
          <!-- 링크 연결된 upload 버튼 -->
          <b-button squared variant="primary" class="mt-3" @click="upload">변환 시작</b-button>
          <b-button squared class="mt-3" @click="$bvModal.hide('bv-modal-example')">취소</b-button>
        </div>
      </b-modal>


          <!-- <p><b-button v-b-popover="'Popover inside a modal!'" title="Popover">음성</b-button></p>
          <p><b-button v-b-popover="'Popover inside a modal!'" title="Popover">자막</b-button></p>
        </div> -->
    </div>

  </div>
</template>

<script>
import UploadService from "../services/UploadFilesService";

export default {
  name: "upload-files",
  data() {
    return {
      selectedFiles: undefined,
      currentFile: undefined,
      progress: 0,
      message: "",
      fileInfos: [],

      // 모달 관련
      uploadData: {
        name: null,
        start: null,
        target: null,
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
    selectFile() {
      console.log("exex")
      this.selectedFiles = this.$refs.file.files
    },
    upload() {
      console.log("start")
      this.progress = 0;
      this.uploadData.name = this.selectedFiles.item(0).name
      this.$emit('submit-upload-data', this.uploadData)
      this.currentFile = this.selectedFiles.item(0);
      this.$emit('upload-file', this.currentFile)
      UploadService.upload(this.currentFile, event => {
        this.progress = Math.round((100 * event.loaded) / event.total);
      })
        .then(response => {
          this.message = response.data.message;
          // return UploadService.getFiles();
        })
        // .then(files => {
        //   this.fileInfos = files.data;
        // })
        // .catch(() => {
        //   this.progress = 0;
        //   this.message = "Could not upload the file!";
        //   this.currentFile = undefined;
        // });
      this.selectedFiles = undefined;
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
  margin-top: -80px;
  margin-left: 1475px;
  
}

.file_input_textbox{float:left}

.file_input_div{position:relative;width:100px;height:23px;overflow:hidden;}

.file_input_button{width:100px;position:absolute; margin-left:3px; top:6px; background-color:#aaa; color:#fff;}

.file_input_hidden{font-size:45px; width:100px; position:absolute; top:0px; opacity:0; 

filter:alpha(opacity=0); 
-ms-filter:"alpha(opacity=0)"; 
-khtml-opacity:0; 
-moz-opacity:0;}


</style>