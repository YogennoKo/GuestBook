<template>
  <div>
    <h1>Guestbook</h1>
    <form @submit.prevent="submitEntry">
      <input v-model="nickname" placeholder="Your nickname" required>
      <textarea v-model="message" placeholder="Your message" required></textarea>
      <button type="submit">Submit</button>
    </form>
    <ul>
      <li v-for="entry in entries" :key="entry.id">
        <strong>{{ entry.nickname }}</strong>: {{ entry.message }}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'GuestbookComponent',
  data() {
    return {
      nickname: '',
      message: '',
      entries: []
    };
  },
  mounted() {
    this.fetchEntries();
  },
  methods: {
    async fetchEntries() {
      try {
        const response = await axios.get('http://localhost:8080/guestbook');
        this.entries = response.data;
      } catch (error) {
        console.error('Error fetching entries:', error);
      }
    },
    async submitEntry() {
      try {
        const newEntry = { nickname: this.nickname, message: this.message };
        await axios.post('http://localhost:8080/guestbook', newEntry);
        this.entries.push(newEntry);
        this.nickname = '';
        this.message = '';
      } catch (error) {
        console.error('Error submitting entry:', error);
      }
    }
  }
};
</script>
