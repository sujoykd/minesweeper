import { css, html, LitElement } from 'lit';
import { customElement, property } from 'lit/decorators.js';

@customElement('game-card')
export class GameCard extends LitElement {

  @property({ attribute: true, type: String })
  cardTitle: string = "";

  static get styles() {
    return css`
        .card {
          display: flex;
          height: 280px;
          width: 200px;
          background-color: rgb(235 235 235);
          border-radius: 10px;
          box-shadow: rgb(90 90 90) -1rem 0px 3rem;
          transition: 0.4s ease-out;
          position: relative;
          left: 0px;
        }
               
        .card:hover {
          transform: translateY(-30px) translateX(-60px);
          transition: 0.4s ease-out;
        }
        
        .card:hover ~ .card {
          position: relative;
          left: 50px;
          transition: 0.4s ease-out;
        }
        
        .title {
          color: #726f6f;
          font-weight: bold;
          position: absolute;
          left: 20px;
          top: 15px;
        }
        
        .bar {
          position: absolute;
          top: 100px;
          left: 20px;
          height: 5px;
          width: 150px;
        }
        
        .emptybar {
          background-color: rgb(235 235 235);
          width: 100%;
          height: 100%;
        }
        
        .filledbar {
          position: absolute;
          top: 0px;
          z-index: 3;
          width: 0px;
          height: 100%;
          background: rgb(0,154,217);
          background: linear-gradient(90deg, rgba(0,154,217,1) 0%, rgba(217,147,0,1) 65%, rgba(255,186,0,1) 100%);
          transition: 0.6s ease-out;
        }
        
        .card:hover .filledbar {
          width: 120px;
          transition: 0.4s ease-out;
        }
        
        .circle {
          position: absolute;
          top: 150px;
          left: calc(50% - 60px);
        }
        
        .stroke {
          stroke: #726f6f;
          stroke-dasharray: 360;
          stroke-dashoffset: 360;
          transition: 0.6s ease-out;
        }
        
        svg {
          fill: rgb(235 235 235);
          stroke-width: 2px;
        }
        
        .card:hover .stroke {
          stroke-dashoffset: 100;
          transition: 0.6s ease-out;
        }
        `;
  }


  render() {
    return html`
            <div class="card">
                <h3 class="title">${this.cardTitle}</h3>
                <div class="bar">
                    <div class="emptybar"></div>
                    <div class="filledbar"></div>
                </div>
                <div class="circle">
                    <svg version="1.1" xmlns="http://www.w3.org/2000/svg">
                        <circle class="stroke" cx="60" cy="60" r="50" />
                    </svg>
                </div>
            </div>
        `;
  }
}