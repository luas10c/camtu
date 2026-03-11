import { StatusBar, StyleSheet, View, Text } from 'react-native'

function App() {
  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" animated />

      <Text style={{ fontSize: 22, color: '#FFFFFF', fontWeight: 600 }}>
        Welcome
      </Text>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#1f1e25'
  }
})

export default App
