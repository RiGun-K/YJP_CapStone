<template>
  <h2>에디터 폼 </h2>
  <div class="editor-page">
    <div id="summernote"></div>
  </div>
  <button id="edit" class="btn btn-primary" onclick="edit()" type="button">Edit 1</button>
  <button id="save" class="btn btn-primary" onclick="save()" type="button">Save 2</button>
  <div class="click2edit">click2edit</div>
</template>

<script>

export default {
  name: "Buyindex",
  mounted() {
    $('#summernote').summernote({
      height: 400,
      minHeight: null,
      maxHeight: null,
      focus: true,
      toolbar: [
        ['style', ['bold', 'italic', 'underline']],
        ['fontsize', ['fontsize']],
        ['color', ['color']],
        ['para', ['paragraph']],
        ['height', ['height']],
        ['Insert', ['picture']],
        ['Mics',['codeview']]
      ]
    });
  },
  methods: {
    edit() {
      $('.click2edit').summernote({focus: true});
    },
    save() {
      const markup = $('.click2edit').summernote('code');
      $('.click2edit').summernote('destroy');
    }
  }


}
</script>

<style scoped>

</style>