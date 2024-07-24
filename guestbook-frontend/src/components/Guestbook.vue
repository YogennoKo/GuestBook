<template>
  <div class="guestbook-container">
    <h1>Guestbook</h1>
    <form @submit.prevent="submitEntry">
      <input v-model="nickname" placeholder="Your nickname" required class="guestbook-input">
      <textarea v-model="message" placeholder="Your message" required class="guestbook-textarea"></textarea>
      <button type="submit" class="guestbook-button">Submit</button>
    </form>
    <ul class="guestbook-entries">
      <li v-for="entry in entries" :key="entry.id" class="guestbook-entry">
        <div class="guestbook-entry-header">
          <strong>{{ entry.nickname }}</strong>
          <span class="guestbook-entry-time">{{ formatTime(entry.timestamp) }}</span>
          <button @click="deleteEntry(entry.id)" class="guestbook-delete-button">Delete</button>
        </div>
        <p>{{ entry.message }}</p>
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
        const newEntry = {nickname: this.nickname, message: this.message};
        await axios.post('http://localhost:8080/guestbook', newEntry);
        this.fetchEntries(); // Refresh the entries after submission
        this.nickname = '';
        this.message = '';
      } catch (error) {
        console.error('Error submitting entry:', error);
      }
    },
    async deleteEntry(id) {
      try {
        await axios.delete(`http://localhost:8080/guestbook/${id}`);
        this.fetchEntries(); // Refresh the entries after deletion
      } catch (error) {
        console.error('Error deleting entry:', error);
      }
    },
    formatTime(timestamp) {
      const date = new Date(timestamp);
      return date.toLocaleString();
    }
  }
};
</script>

<style scoped>
.guestbook-container {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  font-family: Arial, sans-serif;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.guestbook-input,
.guestbook-textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.guestbook-button {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.guestbook-button:hover {
  background-color: #0056b3;
}

.guestbook-entries {
  list-style: none;
  padding: 0;
}

.guestbook-entry {
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.guestbook-entry-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.guestbook-entry-time {
  font-size: 0.9em;
  color: #888;
}

.guestbook-delete-button {
  background: none;
  border: none;
  color: red;
  cursor: pointer;
}

.guestbook-delete-button:hover {
  text-decoration: underline;
}
</style>
