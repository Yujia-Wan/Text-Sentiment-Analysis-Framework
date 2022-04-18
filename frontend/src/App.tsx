import Handlebars from "handlebars"
import { Component } from 'react'
import './App.css'

var oldHref = "http://localhost:3000"

interface Test {
  template: HandlebarsTemplateDelegate<any>
  test: String
}

interface Props {
}

class App extends Component<Props, Test> {
  constructor(props: Props) {
    super(props);
    this.state = {
      template: this.loadTemplate(),
      test: "TEST!!!!!!!!!!!!!"
    };
  }

  loadTemplate(): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById("handlebars");
    return Handlebars.compile(src?.innerHTML, {});
  }

  // async generate() {
    // const href = "generate?" + this.getDataPluginName + "&" + this.getDataPluginIndex + "&" + this.getDisplayPluginName;
    // const response = await fetch(href);
    // const json = await response.json();
  // }

  async switch() {
    if (
      window.location.href.split("?")[0] === "http://localhost:3000" &&
      oldHref !== window.location.href
    ) {
      console.log(window.location.href);
      console.log("hello!!!");

      // this.setDataPluginName(window.location.href.split("?")[1]);
      oldHref = window.location.href;
    }
  };

  render() {
    this.switch()
    return (
      <div className="App">
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({ test: this.state.test }),
          }}
        />
      </div>
    )
  };
};

export default App;
