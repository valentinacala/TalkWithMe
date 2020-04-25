/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import 'react-native-gesture-handler';
import {NavigationContainer} from '@react-navigation/native';
import * as React from 'react';
import { PersistGate } from 'redux-persist/integration/react'
import { createStackNavigator } from '@react-navigation/stack';
import FormScreen from './ts/screens/FormScreen';
import GuidedFormScreen from './ts/screens/GuidedFormScreen';
import FormSavedScreen from './ts/screens/FormSavedScreen';
import { Provider } from 'react-redux';
import configureStoreAndPersistor from './configureStore';
import RecoverOldFormScreen from './ts/screens/RecoverOldFormScreen';
import RecoverFormScreen from './ts/screens/RecoverFormScreen';
import UsersScreen from './ts/screens/UsersScreen';
import CitizenScreen from './ts/screens/CitizenScreen';
import RecoveSearchedFormScreen from './ts/screens/RecoverSearchedFormScreen';
import PoliceScreen from './ts/screens/PoliceScreen';
import QrCodeScreen from './ts/screens/QrCodeScreen';
import QrCodeScannerScreen from './ts/screens/QrCodeScannerScreen';

const Stack = createStackNavigator();

const store = configureStoreAndPersistor().store;
const persistor = configureStoreAndPersistor().persistor;

const App = () => {
  return (
    <Provider store = {store}>
      <PersistGate loading={null} persistor={persistor}>
      <NavigationContainer>
        <Stack.Navigator initialRouteName="Users">

          {/** COMMON */}
          <Stack.Screen name="Users" component={UsersScreen} options={{title: ''}}/>
          <Stack.Screen name="Form" component={FormScreen} options={{title: ''}}/>
          
          {/** CITIZEN */}
          <Stack.Screen name="Citizen" component={CitizenScreen} options={{title: ''}}/>
          <Stack.Screen name="RecoverOld" component={RecoverOldFormScreen} options={{title: ''}}/>
          <Stack.Screen name="GuidedForm" component={GuidedFormScreen} options={{title: ''}}/>
          <Stack.Screen name="Saved" component={FormSavedScreen} options={{title: ''}}/>
          <Stack.Screen name="QrCode" component={QrCodeScreen} options={{title: ''}}/>
          
          {/** POLICE */}
          <Stack.Screen name="Police" component={PoliceScreen} options={{title: ''}}/>
          <Stack.Screen name="SearchedForm" component={RecoveSearchedFormScreen} options={{title: ''}}/>
          <Stack.Screen name="Recover" component={RecoverFormScreen} options={{title: ''}}/>
          <Stack.Screen name="QrCodeScanner" component={QrCodeScannerScreen} options={{title: ''}}/>
          
        </Stack.Navigator>
    </NavigationContainer>
    </PersistGate>
    </Provider>
  );
}

export default App;
