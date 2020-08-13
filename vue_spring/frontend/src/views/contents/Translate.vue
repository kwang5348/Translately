<template>
  <div class="wrapper">
    <div class="mb-3 mt-5">
      <p style="font-size: 20px;"><b>유튜브 영상 URL로 번역</b></p>
      <upload-files1></upload-files1>
      <!-- <img src="..\..\img\urlbox.png" alt="url 박스 임시" class="url-box">     -->
    </div>
    <p style="font-size: 20px;"><b>또는</b></p>
    <div class="mt-3">
    <p style="font-size: 20px; margin-bottom: 5px;"><b>내 컴퓨터에 있는 영상 번역</b></p>
    <p style="font-size: 18px; margin-bottom: 30px;">'파일 선택' 버튼을 누르거나 마우스로 파일을 이곳에 끌어서 놓으세요.</p>
    </div>
    <upload-files @upload-file="uploadFile" @submit-upload-option="uploadOption"></upload-files>
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

</style>