<template>
  <div>
    <tr>
      <td>{{ start }} ~</td>
      <td>{{ end }}</td>
      <td>{{ subtitle.kor }}</td>
    </tr>
  </div>
</template>

<script>
export default {
  name: "subtitle-vue",
  props: {
    subtitle: {
      type: Object
    }
  },
  computed: {
    start() {
      return this.subtitle.startTime.toFixed(2)
    },
    end() {
      return this.subtitle.endTime.toFixed(2)
    }
  }


}
</script>

<style>

</style>