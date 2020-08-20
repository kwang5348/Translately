<template>  
  <div class="row sub-box">
    <div class="col-2">
      {{ index + 1 }}
    </div>
    <div class="col-8" style="padding: 0px; text-align: left;">
      <v-row>
      <v-col cols="6" md="12">
        <v-textarea
          solo
          auto-grow
          row-height="20"
          rows="2"
          name="input-7-4"
          v-model="subtitleInput"
        ></v-textarea>
      </v-col>
      </v-row>
    </div>
    <div class="col-1" style="padding: 0px;">
      <p style="font-size: 13px; margin:0px">{{ start }} ~</p>
      <p style="font-size: 13px; margin:0px">{{ end }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "subtitle-vue",
  props: {
    subtitle: {
      type: Object
    },
    index: {
      type: Number
    }
  },
  data() {
    return {
      subtitleInput: "",
    }
  },
  methods: {
    
  },
  created() {
    this.subtitleInput = this.subtitle.targetsub
  },
  computed: {
    start() {
      return this.subtitle.startTime.toFixed(2)
    },
    end() {
      return this.subtitle.endTime.toFixed(2)
    }
  },
  watch: {
    subtitleInput() {
      const subtitleData = {
        "subtitleInput": this.subtitleInput,
        "index": this.index
      }
      this.$emit('submit-subtitle-input', subtitleData)
    }
  }

}
</script>

<style>
.sub-box {
  background-color: transparent;
  border-color: black;
  padding: 0px;
}

.target-subtitle {
  margin-bottom: 0px;
}

.division-line {
  margin: 10px 20px;
}

</style>