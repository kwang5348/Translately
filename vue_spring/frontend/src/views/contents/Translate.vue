<template>
  <div class="wrapper">
    <h1 class="block">자막을 생성하고 싶은 영상을 업로드 하세요.</h1> 
    <!-- <h5>‘파일 선택‘ 버튼을 누르거나 마우스로 파일을 이곳에 끌어서 놓으세요.</h5> -->
    <upload-files1 @submit-upload-option="uploadOption"></upload-files1>
    
    
    <upload-files @upload-file="uploadFile" @submit-upload-option="uploadOption"></upload-files>
  </div>
</template>

<script>
import UploadFiles1 from "../../components/UploadFiles1"
import UploadFiles from "../../components/UploadFiles"


  export default {
    name: 'Translate',
    components: {
      UploadFiles,
      UploadFiles1
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