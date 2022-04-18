import Handlebars from 'handlebars'
import { Data } from 'plotly.js'
import { Component } from 'react'
import Plot from 'react-plotly.js'
import './App.css'


var oldHref = "http://localhost:3000"


interface FrameworkState {
  instruction: String;
  template: HandlebarsTemplateDelegate<any>;
}

interface Props {
}

let json: Data

class App extends Component<Props, FrameworkState> {
  constructor(props: Props) {
    super(props);
    this.state = {
      instruction: "hello",
      template: this.loadTemplate(),
    };
  }

  loadTemplate(): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById("handlebars");
    return Handlebars.compile(src?.innerHTML, {});
  }

  getInstruction(p: any): any {
    return p["instruction"]
  }


  async submit(url: String) {
    const href = "submit?" + url.split("?")[1];
    const response = await fetch(href);
    const json1 = await response.json();
    const instruction = this.getInstruction(json1);
    // console.log(instruction);

    this.setData(instruction);
    this.setState({ instruction: "hello" });
  }

  async setData(instruction: any) {
    // const data = '{"x":[2,1,2],"type":"pie","labels":["Positive",' + '"'+ instruction  + '"' + ' ,"Negative"]}';
    const data = '{"x":["a","b","c"],"type":"bar","y":[0.8,0.9,0.1]}';
    const jsontest = JSON.parse(data)
    console.log(jsontest)
    console.log("23232232")
    
    
    // const data2 =    instruction ;
    console.log("--------compare")
    // console.log(data2);
    json = instruction;
    console.log(json)
   
  }
  


  async switch() {
    if (
      window.location.href.split("?")[0] === "http://localhost:3000/submit" &&
      oldHref !== window.location.href
    ) {
      this.submit(window.location.href);
      oldHref = window.location.href;
    }
  }

  render() {
    this.switch()
  
    return (
      <div className="App">
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({ instruction: this.state.instruction }),
          }}
        />
        <Plot
        data={[
          json,
        ]}
        layout={ {width: 500, height: 400} }
      />
      </div>
    )
  };
};

export default App;
