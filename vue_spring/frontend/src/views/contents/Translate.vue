<template>
  <div class="wrapper font">
    <div class="row mb-3" style="margin-top: 80px;">
      <div class="col-6 section1">
        <p style="font-size: 20px; margin-bottom: 5px;"><b>유튜브 영상 URL로 번역</b></p>
        <p style="font-size: 18px; margin-bottom: 30px;">번역하고 싶은 영상의 유튜브 URL을 입력하세요.</p>
        <upload-files1 @upload-file="uploadFile" @submit-upload-option="uploadOption"></upload-files1>
      </div>
      <div class="col-6 section2">
        <p style="font-size: 20px; margin-bottom: 5px;"><b>내 컴퓨터에 있는 영상 번역</b></p>
        <p style="font-size: 18px; margin-bottom: 30px;">'파일 선택' 버튼을 누르거나 마우스로 파일을 이곳에 끌어서 놓으세요.</p>
        <upload-files @upload-file="uploadFile" @submit-upload-option="uploadOption"></upload-files> 
      </div>
    </div>
  </div>
</template>

<script>
import UploadFiles1 from "../../components/UploadFiles1"
import UploadFiles from "../../components/UploadFiles"


export default {
  name: 'Translate',
  components: {
    UploadFiles1,
    UploadFiles,
  },
  data() {
    return {
    }
  },
  methods: {
    uploadFile(video) {
      this.$emit('upload-file', video)
    },
    uploadOption(uploadData) {
      this.$emit('submit-upload-option', uploadData)
    },
  },
}
</script>

<style scoped>
.font {
  margin-bottom: 0px;
  font-family: 'InfinitySans-RegularA1'
}

.section1 {
  padding: 12px 0px 12px 30px;
}

.section2 {
  padding: 12px 30px 12px 0px;
}
</style>