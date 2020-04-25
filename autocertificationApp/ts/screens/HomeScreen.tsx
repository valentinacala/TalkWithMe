import { View, Image, StyleSheet } from "react-native";
import { Button, Text, Content, Container } from 'native-base'
import * as React from "react";
import { commonStyles } from "../utils/commonStyles";

type Props = {
    navigation: any
};

function HomeScreen(props: Props) {
    const SpacerView = (
        <View style={commonStyles.spacer}></View>
    )

    return(
        <Container style={commonStyles.container}>

        <Content style={commonStyles.content}>
                {SpacerView}
                <Text>Hello World!</Text>
                {SpacerView}

         </Content>
         </Container>

    );
}

export default  HomeScreen;